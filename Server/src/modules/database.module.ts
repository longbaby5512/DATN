import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { databaseConfig } from 'src/configs/database.config';

@Module({
  imports: [TypeOrmModule.forRootAsync(databaseConfig)],
})
export class DatabaseModule {}
