import { Exclude } from 'class-transformer';
import { HashingService } from '../../security';
import {
  BeforeInsert,
  BeforeUpdate,
  Column,
  Entity,
  Index,
  OneToOne,
} from 'typeorm';
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
  @Index({ unique: true })
  email: string;

  @Column({
    type: 'varchar',
    length: 255,
    nullable: false,
  })
  firstName: string;

  @Column({
    type: 'varchar',
    length: 255,
    nullable: false,
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

  @Column({
    type: 'boolean',
    nullable: false,
  })
  @Exclude()
  status: boolean;

  @Column({
    type: 'integer',
    nullable: false,
    default: 0,
  })
  numberOfDevices: number;

  @BeforeInsert()
  async hashPassword() {
    const { hash, salt } = await HashingService.hash(
      this.password,
      parseInt(process.env.SALT_LENGTH, 10),
    );
    this.password = hash;
    this.salt = salt;
    this.status = this.numberOfDevices > 0;
  }

  @BeforeUpdate()
  async checkActive() {
    this.status = this.numberOfDevices > 0;
  }
}
