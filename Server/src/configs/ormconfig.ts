require('dotenv').config();

export = {
  type: 'postgres',
  host: process.env.DB_HOST || 'localhost',
  port: parseInt(process.env.DB_PORT) || 5432,
  username: process.env.DB_USERNAME || 'postgres',
  password: process.env.DB_PASSWORD || 'postgres',
  database: process.env.DB_DATABASE || 'testdb',
  entities: [__dirname + '/../models/entities/**/*.entity{.ts,.js}'],
  logging: process.env.DB_LOGGING || 'all',
  migrations: [__dirname + '/../../src/database/migrations/**/*{.ts,.js}'],
  migrationsRun: process.env.DB_MIGRATIONS_RUN || true,
  migrationsTableName:
    process.env.DB_MIGRATIONS_TABLE_NAME || 'migration_table',
  cli: {
    migrationsDir: 'src/database/migrations',
    entitiesDir: 'src/models/entities',
    subscribersDir: 'src/models/subscribers',
  },
  seeds: ['src/database/seeding/seeds/**/*{.ts,.js}'],
  factories: ['src/database/seeding/factories/**/*{.ts,.js}'],
  ssl: process.env.NODE_ENV === 'PROD',
};
