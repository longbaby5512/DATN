import { Module } from '@nestjs/common';
import { UserModule } from './user.module';
import { PassportModule } from '@nestjs/passport';
import { jwtConfig } from '../configs/jwt.config';
import { JwtStrategy } from '../auth/jwt.strategy';
import { EcdhController } from '../ecdh/ecdh.controller';
import { ConfigModule } from '@nestjs/config';
import { JwtModule } from '@nestjs/jwt';

@Module({
  imports: [
    UserModule,
    PassportModule.register({
      defaultStrategy: 'jwt',
      property: 'user',
      session: false,
    }),
    ConfigModule,
    JwtModule.registerAsync(jwtConfig),
  ],
  providers: [JwtStrategy],
  controllers: [EcdhController],
})
export class ECDHModule {}
