import { Module } from '@nestjs/common';
import { Connection } from 'typeorm';
import { UserModule } from './user.module';
import { DatabaseModule } from './database.module';
import { AuthModule } from './auth.module';
import { ConfigModule } from '@nestjs/config';
import Joi from 'joi';
import { ECDHModule } from './echd.module';
import { ChatModule } from './chat.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      validationSchema: Joi.object({
        NODE_ENV: Joi.string(),
        DB_HOST: Joi.string().required(),
        DB_PORT: Joi.number().required(),
        DB_USERNAME: Joi.string().required(),
        DB_PASSWORD: Joi.string().required(),
        DB_DATABASE: Joi.string().required(),
        JWT_PRIVATE_KEY: Joi.string().required(),
        JWT_EXPIRES_IN: Joi.number().required(),
        JWT_PUBLIC_KEY: Joi.string().required(),
        JWT_ALGORITHM: Joi.string().required(),
      }),
    }),
    DatabaseModule,
    UserModule,
    AuthModule,
    ECDHModule,
    ChatModule,
  ],
})
export class AppModule {
  constructor(private readonly connection: Connection) {}
}
