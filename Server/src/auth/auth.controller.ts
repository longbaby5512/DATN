import {
  Body,
  Controller,
  Get,
  HttpCode,
  HttpStatus,
  Post,
  UseGuards,
} from '@nestjs/common';
import { AuthService } from './auth.service';
import { LocalAuthGuard } from './local.guard';
import JwtAuthGuard from './jwt-auth.guard';
import { GetUser } from '../models/decorators';
import { CreateUserDTO, UserDTO } from '../models/dto';

@Controller('auth')
export class AuthController {
  constructor(private readonly service: AuthService) { }

  @Post('register')
  async register(@Body() registationData: CreateUserDTO) {
    await this.service.register(registationData);
    return {
      statusCode: HttpStatus.CREATED,
      message: 'User has been created',
    };
  }

  @UseGuards(LocalAuthGuard)
  @Post('login')
  @HttpCode(HttpStatus.OK)
  async login(@GetUser() user: UserDTO) {
    const token = await this.service.getAccessToken(user.id);
    user.password = undefined;
    user.salt = undefined;
    user.numberOfDevices = undefined;
    return {
      statusCode: HttpStatus.OK,
      data: {
        user: user,
        accessToken: token.accessToken,
      },
    };
  }

  @UseGuards(JwtAuthGuard)
  @HttpCode(HttpStatus.OK)
  @Post('logout')
  async logout(@GetUser() user: UserDTO) {
    await this.service.logout(user);
    return {
      statusCode: HttpStatus.OK,
      message: 'User has been logged out',
    };
  }

  @UseGuards(JwtAuthGuard)
  @Get('whoami')
  authenticate(@GetUser() user: UserDTO) {
    user.password = undefined;
    user.salt = undefined;
    const active: boolean = user.numberOfDevices > 0;
    user.numberOfDevices = undefined;
    return {
      statusCode: HttpStatus.OK,
      data: {
        user: {
          ...user,
          status: active,
        },
      },
    };
  }
}
