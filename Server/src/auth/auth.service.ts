import { HttpException, HttpStatus, Injectable, Logger } from '@nestjs/common';
import { HashingService, SaltedHash } from '../security';
import { UserService } from '../user/user.service';
import { JwtService } from '@nestjs/jwt';
import PostgresErrorCode from '../database/postgres-error-code';
import { JwtPayload } from '../models/interfaces';
import { CreateUserDTO, LoginUserDTO, UserDTO } from '../models/dto';

@Injectable()
export class AuthService {
  private logger: Logger;

  constructor(
    private readonly userService: UserService,
    private readonly jwtService: JwtService,
  ) {
    this.logger = new Logger();
  }

  register = async (registrationData: CreateUserDTO) => {
    try {
      registrationData.email = registrationData.email.toLowerCase();
      await this.userService.create(registrationData);
      this.logger.log(`User with email: ${registrationData.email} created successfully`);
    } catch (error) {
      if (error.code === PostgresErrorCode.UNIQUE_VIOLATION) {
        this.logger.error(`Email ${registrationData.email} already exists in the database`)
        throw new HttpException('Email already exists', HttpStatus.BAD_REQUEST);
      }
      this.logger.error("Internal server error")
      throw new HttpException(
        'Internal server error',
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
  };

  login = async (loginUserDto: LoginUserDTO) => {
    const email = loginUserDto.email.toLowerCase();
    const password = loginUserDto.password;
    try {
      const user = await this.userService.getByEmail(email);
      const { password: hash, salt } = user;
      await this.verifyPassword(password, { hash, salt });
      user.numberOfDevices = user.numberOfDevices + 1;
      this.logger.log(`User with email ${email} logged in`)
      return await this.userService.update(user.toUpdateDTO());
    } catch (error) {
      this.logger.error(error.message);
      throw new HttpException("Email or password incorrect", HttpStatus.UNAUTHORIZED);
    }
  };

  logout = async (user: UserDTO) => {
    user.numberOfDevices = user.numberOfDevices - 1;
    if (user.numberOfDevices < 0) {
      throw new HttpException('User has no devices', HttpStatus.BAD_REQUEST);
    }
    await this.userService.update(user.toUpdateDTO());
    this.logger.log(`User with email ${user.email} logout successfully`)
  };

  verifyPassword = async (password: string, saltedHash: SaltedHash) => {
    const { hash, salt } = saltedHash;
    const isValid = await HashingService.compare(password, { hash, salt });
    if (!isValid) {
      throw new HttpException('Email or password incorrect', HttpStatus.UNAUTHORIZED);
    }
  };

  validateUser = async (payload: JwtPayload) => {
    const user = await this.userService.getById(payload.userId);
    if (!user) {
      throw new HttpException('User not found', HttpStatus.UNAUTHORIZED);
    }
    user.password = undefined;
    user.salt = undefined;
    return user;
  };

  getAccessToken = async (userId: string) => {
    const payload: JwtPayload = { userId: userId };
    const token = await this.jwtService.signAsync(payload);
    return {
      accessToken: token,
    };
  };
}
