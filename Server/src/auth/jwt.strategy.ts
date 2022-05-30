import { ExtractJwt, Strategy } from 'passport-jwt';
import { PassportStrategy } from '@nestjs/passport';
import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { UserService } from '../user/user.service';
import { JwtPayload } from '../models/interfaces';
import { UserDTO } from '../models/dto';
import { Request } from 'express';
import console from 'console';
import { readFileSync } from 'fs';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(
    private readonly configService: ConfigService,
    private readonly userService: UserService,
  ) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      secretOrKey: readFileSync(configService.get('JWT_PUBLIC_KEY')),
      algorithms: ['RS256'],
    });
  }

  async validate(payload: JwtPayload) {
    console.log(JSON.stringify(payload));
    const user = await this.userService.getById(payload.userId);
    if (!user) {
      throw new HttpException(
        'Token is unauthenticated',
        HttpStatus.UNAUTHORIZED,
      );
    }
    return UserDTO.fromEntity(user);
  }
}
