import {
  Body,
  Controller,
  Get,
  HttpStatus,
  Post,
  Res,
  UseGuards,
} from '@nestjs/common';
import { CreateUserDTO, UserDTO } from '../models/dto';
import { AuthService } from './auth.service';
import { LocalAuthGuard } from './local.guard';
import { Response } from 'express';
import JwtAuthGuard from './jwt-auth.guard';
import { GetUser } from 'src/models/decorators';
import snakecaseKeys from 'snakecase-keys';

@Controller('auth')
export class AuthController {
  constructor(private readonly service: AuthService) { }

  @Post('register')
  async register(@Body() registationData: CreateUserDTO, @Res() res: Response) {
    const userDTO = await this.service.register(registationData);
    return this.login(userDTO, res);
  }

  @UseGuards(LocalAuthGuard)
  @Post('login')
  async login(@GetUser() user: UserDTO, @Res() res: Response) {
    const token = await this.service.getAccessToken(user.id);
    res.status(HttpStatus.OK).json(snakecaseKeys({
      user,
      accessToken: token.accessToken,
    }));
  }

  @UseGuards(JwtAuthGuard)
  @Get('whoami')
  authenticate(@GetUser() user: UserDTO) {
    return snakecaseKeys(user);
  }
}
