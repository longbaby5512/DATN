import { Module } from '@nestjs/common';
import { Connection } from 'typeorm';
import { AppController } from '../app/app.controller';
import { AppService } from '../app/app.service';
import { UserModule } from './user.module';
import { DatabaseModule } from './database.module';
import { AuthModule } from './auth.module';
import { ConfigModule } from '@nestjs/config';
import Joi from 'joi';
import { ECDHModule } from './echd.module';
import { ChatModule } from './chat.module';
import { LoggerModule } from 'nestjs-pino';
import { pinoConfig } from '../configs/pino.config';

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
        JWT_SECRET: Joi.string().required(),
        JWT_EXPIRES_IN: Joi.string().required(),
      }),
    }),
    DatabaseModule,
    UserModule,
    AuthModule,
    ECDHModule,
    ChatModule,
    LoggerModule.forRootAsync(pinoConfig)
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
  constructor(private readonly connection: Connection) { }
}
