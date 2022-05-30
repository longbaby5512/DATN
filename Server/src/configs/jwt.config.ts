import { ConfigModule, ConfigService } from '@nestjs/config';
import { JwtModuleOptions } from '@nestjs/jwt';
import { readFileSync } from 'fs';

export class JwtConfig {
  static getJwtConfig(configService: ConfigService): JwtModuleOptions {
    const privateKey = readFileSync(configService.get('JWT_PRIVATE_KEY'));
    const result: JwtModuleOptions = {
      secret: privateKey,
      signOptions: {
        expiresIn: `${configService.get('JWT_EXPIRES_IN')}s`,
        algorithm: configService.get('JWT_ALGORITHM'),
      },
    };
    return result;
  }
}

export const jwtConfig = {
  imports: [ConfigModule],
  inject: [ConfigService],
  useFactory: async (configService: ConfigService) =>
    JwtConfig.getJwtConfig(configService),
};
