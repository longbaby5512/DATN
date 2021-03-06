import { MigrationInterface, QueryRunner } from 'typeorm';

export class initInfomationTable1657922111269 implements MigrationInterface {
  name = 'initInfomationTable1657922111269';

  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.query(`
            CREATE TABLE "informations" (
                "id" integer GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                "user_id" integer,
                "socket_id" text,
                "created_at" TIMESTAMP NOT NULL DEFAULT now(),
                "updated_at" TIMESTAMP NOT NULL DEFAULT now(),
                CONSTRAINT "PK_3e27903b20087cf4d880bb91ac3" PRIMARY KEY ("id")
            )
        `);
    await queryRunner.query(`
            CREATE INDEX "IDX_5b7c03296b7da9206b6c7b054e" ON "informations" ("user_id")
        `);
    await queryRunner.query(`
            ALTER TABLE "informations"
            ADD CONSTRAINT "FK_5b7c03296b7da9206b6c7b054e1" FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE NO ACTION ON UPDATE NO ACTION
        `);
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.query(`
            ALTER TABLE "informations" DROP CONSTRAINT "FK_5b7c03296b7da9206b6c7b054e1"
        `);
    await queryRunner.query(`
            DROP INDEX "public"."IDX_5b7c03296b7da9206b6c7b054e"
        `);
    await queryRunner.query(`
            DROP TABLE "informations"
        `);
  }
}
