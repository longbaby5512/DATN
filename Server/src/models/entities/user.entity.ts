import { Exclude } from 'class-transformer';
import { HashingService } from '../../security';
import { BeforeInsert, BeforeUpdate, Column, Entity } from 'typeorm';
import { IUser } from '../interfaces';
import { Base } from '.';

@Entity({ name: 'users' })
export class User extends Base implements IUser {
  @Column({
    type: 'varchar',
    length: 255,
    nullable: false,
    unique: true,
  })
  email: string;

  @Column({
    type: 'varchar',
    length: 255,
    nullable: false,
    name: 'first_name',
  })
  firstName: string;

  @Column({
    type: 'varchar',
    length: 255,
    nullable: false,
    name: 'last_name',
  })
  lastName: string;

  @Column({
    type: 'text',
    nullable: false,
  })
  @Exclude()
  password: string;

  @Column({
    type: 'text',
    nullable: false,
  })
  @Exclude()
  salt: string;

  @BeforeInsert()
  @BeforeUpdate()
  async hashPassword() {
    const { hash, salt } = await HashingService.hash(
      this.password,
      parseInt(process.env.SALT_LENGTH, 10),
    );
    this.password = hash;
    this.salt = salt;
  }
}
