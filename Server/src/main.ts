import { NestFactory } from '@nestjs/core';
import { AppModule } from './modules/app.module';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import * as helmet from 'helmet';
import * as compression from 'compression';
import { ValidationPipe } from '@nestjs/common';
import { Logger } from 'nestjs-pino';
import { readFile, readFileSync } from 'fs';
import express from 'express';
import { ExpressAdapter } from '@nestjs/platform-express';
import * as http from 'http';
import * as https from 'https';
import { ConfigService } from '@nestjs/config';

require('dotenv').config();


async function bootstrap() {

  const configService = new ConfigService();

  const httpsOptions: https.ServerOptions = {
    key: readFileSync(configService.get('SSL_KEY')),
    cert: readFileSync(configService.get('SSL_CERT')),
  };

  const server = express();

  const app = await NestFactory.create(AppModule, new ExpressAdapter(server), {
    httpsOptions,
  });
  app.enableCors();
  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
    }),
  );


  const logger = app.get(Logger);

  app.useLogger(logger);
  app.flushLogs();

  const config = new DocumentBuilder()
    .setTitle('Example')
    .setDescription('The example API description')
    .setVersion('1.0')
    .addTag('example')
    .build();
  const document = SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('api', app, document);

  const isProduction = configService.get<string>('NODE_ENV') === 'PROD';

  if (isProduction) {
    app.use(helmet.default());
    app.use(compression.default());
  }

  await app.init();
  http.createServer(server).listen(configService.get('HTTP_PORT'), () => {
    logger.log(
      `HTTP server listening on port ${configService.get('HTTP_PORT')}`,
    );
  });
  https
    .createServer(httpsOptions, server)
    .listen(configService.get('HTTPS_PORT'), () => {
      logger.log(
        `HTTPS server listening on port ${configService.get('HTTPS_PORT')}`,
      );
    });
}
bootstrap();
