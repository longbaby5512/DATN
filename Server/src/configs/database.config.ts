import { ConfigModule, ConfigService } from '@nestjs/config';
import {
  TypeOrmModuleAsyncOptions,
  TypeOrmModuleOptions,
} from '@nestjs/typeorm';

export class TypeOrmConfig {
  static getOrmConfig = (
    configService: ConfigService,
  ): TypeOrmModuleOptions => {
    const result: TypeOrmModuleOptions = {
      type: 'postgres',
      host: configService.get('DB_HOST'),
      port: configService.get('DB_PORT'),
      username: configService.get('DB_USERNAME'),
      password: configService.get('DB_PASSWORD'),
      database: configService.get('DB_DATABASE'),
      entities: [__dirname + '/../models/entities/**/*.entity{.ts,.js}'],
      logging: configService.get('DB_LOGGING'),
      migrations: [__dirname + '/../../src/migrations/*{.ts,.js}'],
      migrationsRun: configService.get('DB_MIGRATIONS_RUN'),
      migrationsTableName: configService.get('DB_MIGRATIONS_TABLE_NAME'),
      cli: {
        migrationsDir: 'src/database/migrations',
        entitiesDir: 'src/models/entities',
        subscribersDir: 'src/models/subscribers',
      },
      ssl: TypeOrmConfig.isProduction(configService),
    };
    return result;
  };
  static isProduction = (configService: ConfigService): boolean => {
    return configService.get('NODE_ENV') === 'PROD';
  };
}

export const databaseConfig: TypeOrmModuleAsyncOptions = {
  imports: [ConfigModule],
  useFactory: async (
    configService: ConfigService,
  ): Promise<TypeOrmModuleOptions> => TypeOrmConfig.getOrmConfig(configService),
  inject: [ConfigService],
};
