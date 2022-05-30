import { ApiProperty } from '@nestjs/swagger';
import {
  IsEmail,
  IsNotEmpty,
  IsString,
  IsUUID,
  MinLength,
} from 'class-validator';
import { User } from '../entities';

export class CreateUserDTO implements Readonly<CreateUserDTO> {
  @ApiProperty({ required: true })
  @IsNotEmpty()
  @IsEmail()
  email: string;

  @ApiProperty({ required: true })
  @IsString()
  @IsNotEmpty()
  @MinLength(8)
  password: string;

  @ApiProperty({ required: true, name: 'first_name' })
  @IsString()
  @IsNotEmpty()
  firstName: string;

  @ApiProperty({ required: true, name: 'last_name' })
  @IsString()
  @IsNotEmpty()
  lastName: string;

  static fromEntity = (user: User): CreateUserDTO => {
    const createUserDTO = new CreateUserDTO();
    createUserDTO.email = user.email;
    createUserDTO.password = user.password;
    createUserDTO.firstName = user.firstName;
    createUserDTO.lastName = user.lastName;
    return createUserDTO;
  };

  toEntity(): User {
    const user = new User();
    user.email = this.email;
    user.password = this.password;
    user.firstName = this.firstName;
    user.lastName = this.lastName;
    return user;
  }
}

export class UserDTO implements Readonly<UserDTO> {
  @IsNotEmpty()
  @IsEmail()
  email: string;

  @IsString()
  @IsNotEmpty()
  firstName: string;

  @IsString()
  @IsNotEmpty()
  lastName: string;

  @IsUUID()
  @IsNotEmpty()
  id: string;

  static fromEntity = (user: User): UserDTO => {
    const userDTO = new UserDTO();
    userDTO.email = user.email;
    userDTO.firstName = user.firstName;
    userDTO.lastName = user.lastName;
    userDTO.id = user.id;
    return userDTO;
  };

  toEntity = (): User => {
    const user = new User();
    user.email = this.email;
    user.firstName = this.firstName;
    user.lastName = this.lastName;
    user.id = this.id;
    return user;
  };
}

export class LoginUserDTO implements Readonly<LoginUserDTO> {
  @IsNotEmpty()
  @IsEmail()
  @ApiProperty({ required: true })
  email: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({ required: true })
  password: string;
}
