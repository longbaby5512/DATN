import { JwtService } from '@nestjs/jwt';
import { User } from '../models/entities';
import { UserService } from '../user/user.service';
import { Repository } from 'typeorm';
import { AuthService } from './auth.service';
import { v4 as uuid4 } from 'uuid';
require('dotenv').config();

describe('AuthService', () => {
  const authService = new AuthService(
    new UserService(new Repository<User>()),
    new JwtService({
      secret: process.env.JWT_SECRET,
    }),
  );
  describe('when creating JWT token', () => {
    it('should return a string', async () => {
      const userId = uuid4();
      expect(typeof authService.getAccessToken(userId)).toEqual('string');
    });
  });
});
