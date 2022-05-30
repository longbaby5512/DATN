import { Injectable } from '@nestjs/common';
import { PassportStrategy } from '@nestjs/passport';
import { Strategy } from 'passport-local';
import { UserDTO } from 'src/models/dto';
import { AuthService } from './auth.service';

@Injectable()
export class LocalStrategy extends PassportStrategy(Strategy) {
  constructor(private service: AuthService) {
    super({ usernameField: 'email' });
  }
  validate = async (email: string, password: string): Promise<UserDTO> => {
    const userDTO = await this.service.login({ email, password });
    return userDTO;
  };
}
