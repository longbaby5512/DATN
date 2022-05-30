import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { HashingService, SaltedHash } from '../security';
import { CreateUserDTO, LoginUserDTO, UserDTO } from '../models/dto';
import { UserService } from '../user/user.service';
import { JwtService } from '@nestjs/jwt';
import PostgresErrorCode from '../database/postgres-error-code';
import { JwtPayload } from '../models/interfaces';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class AuthService {
  constructor(
    private readonly userService: UserService,
    private readonly jwtService: JwtService,
  ) {}

  register = async (registrationData: CreateUserDTO) => {
    try {
      const createUser = await this.userService.create(registrationData);
      return createUser;
    } catch (error) {
      if (error.code === PostgresErrorCode.UNIQUE_VIOLATION) {
        throw new HttpException('Email already exists', HttpStatus.BAD_REQUEST);
      }
      throw new HttpException(
        'Internal server error',
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
  };

  login = async (loginUserDto: LoginUserDTO) => {
    const { email, password } = loginUserDto;
    try {
      const user = await this.userService.getByEmail(email);
      if (!user) {
        throw new HttpException('User not found', HttpStatus.NOT_FOUND);
      }
      const { password: hash, salt } = user;
      await this.verifyPassword(password, { hash, salt });
      const userDTO = UserDTO.fromEntity(user);
      return userDTO;
    } catch (error) {
      throw new HttpException(error.message, error.status);
    }
  };

  verifyPassword = async (password: string, saltedHash: SaltedHash) => {
    const { hash, salt } = saltedHash;
    const isValid = await HashingService.compare(password, { hash, salt });
    if (!isValid) {
      throw new HttpException('Invalid credentials', HttpStatus.UNAUTHORIZED);
    }
  };

  validateUser = async (payload: JwtPayload) => {
    const user = await this.userService.getById(payload.userId);
    if (!user) {
      throw new HttpException('User not found', HttpStatus.UNAUTHORIZED);
    }
    const userDTO = UserDTO.fromEntity(user);
    return userDTO;
  };

  getAccessToken = async (userId: string) => {
    const payload: JwtPayload = { userId: userId };
    const token = await this.jwtService.signAsync(payload);
    return {
      accessToken: token,
    };
  };
}
