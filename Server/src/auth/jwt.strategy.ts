import { ExtractJwt, Strategy } from 'passport-jwt';
import { PassportStrategy } from '@nestjs/passport';
import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { UserService } from '../user/user.service';
import { JwtPayload } from '../models/interfaces';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(
    private readonly configService: ConfigService,
    private readonly userService: UserService,
  ) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      secretOrKey: configService.get('JWT_PUBLIC_KEY'),
      algorithms: [configService.get('JWT_ALGORITHM')],
    });
  }

  async validate(payload: JwtPayload) {
    const user = await this.userService.getById(payload.userId);
    if (!user) {
      throw new HttpException(
        'Token is unauthenticated',
        HttpStatus.UNAUTHORIZED,
      );
    }
    user.password = undefined;
    user.salt = undefined;
    return user;
  }
}
