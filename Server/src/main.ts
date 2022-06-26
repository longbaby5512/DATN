import { NestFactory } from '@nestjs/core';
import { AppModule } from './modules/app.module';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import * as helmet from 'helmet';
import * as compression from 'compression';
import { Logger, ValidationPipe } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

require('dotenv').config();

async function bootstrap() {
  const configService = new ConfigService();
  const isProduction = configService.get<string>('NODE_ENV') === 'production';


  const app = await NestFactory.create(AppModule, {
    logger: ['error', 'warn', 'debug', 'log', 'verbose'],
  });
  app.enableCors();
  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
    }),
  );

  const config = new DocumentBuilder()
    .setTitle('Example')
    .setDescription('The example API description')
    .setVersion('1.0')
    .addTag('example')
    .build();
  const document = SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('api', app, document);

  if (isProduction) {
    app.use(helmet.default());
    app.use(compression.default());
  }

  const port = configService.get<number>('PORT') || 3000;

  await app
    .listen(port)
    .then(() => {
      new Logger().log(`Server running on port ${port}`);
    })
    .catch((err) => {
      new Logger().error(err);
    });
}
bootstrap();
