import { ApiProperty, OmitType } from '@nestjs/swagger';
import {
  IsBoolean,
  IsEmail,
  IsInt,
  IsNotEmpty,
  IsString,
  IsUUID,
  Min,
  MinLength,
} from 'class-validator';
import { User } from '../entities';

export class UserDTO implements Readonly<UserDTO> {
  @IsNotEmpty()
  @IsUUID()
  id: string;

  @ApiProperty({ required: true })
  @IsNotEmpty()
  @IsEmail()
  email: string;

  @ApiProperty({ required: true })
  @IsString()
  @IsNotEmpty()
  @MinLength(8)
  password: string;

  salt: string;

  @ApiProperty({ required: true })
  @IsString()
  @IsNotEmpty()
  firstName: string;

  @ApiProperty({ required: true })
  @IsString()
  @IsNotEmpty()
  lastName: string;

  @IsInt()
  @Min(0)
  numberOfDevices: number;

  static fromEntity(user: User): UserDTO {
    const userDTO = new UserDTO();
    userDTO.id = user.id;
    userDTO.email = user.email;
    userDTO.password = user.password;
    userDTO.salt = user.salt;
    userDTO.firstName = user.firstName;
    userDTO.lastName = user.lastName;
    userDTO.numberOfDevices = user.numberOfDevices;
    return userDTO;
  }

  toEntity(): User {
    const user = new User();
    user.id = this.id;
    user.email = this.email;
    user.password = this.password;
    user.salt = this.salt;
    user.firstName = this.firstName;
    user.lastName = this.lastName;
    user.numberOfDevices = this.numberOfDevices;
    return user;
  }

  toUpdateDTO(): UpdatedUserDTO {
    const updatedUserDTO = new UpdatedUserDTO();
    updatedUserDTO.id = this.id;
    updatedUserDTO.email = this.email;
    updatedUserDTO.firstName = this.firstName;
    updatedUserDTO.lastName = this.lastName;
    updatedUserDTO.numberOfDevices = this.numberOfDevices;
    return updatedUserDTO;
  }
}

export class CreateUserDTO
  extends OmitType(UserDTO, ['id', 'salt', 'numberOfDevices', 'toUpdateDTO'])
  implements Readonly<CreateUserDTO> {}

export class UpdatedUserDTO
  extends OmitType(UserDTO, ['salt', 'password', 'toEntity', 'toUpdateDTO'])
  implements Readonly<UpdatedUserDTO> {}

export class LoginUserDTO
  extends OmitType(UserDTO, [
    'firstName',
    'lastName',
    'id',
    'salt',
    'toEntity',
    'numberOfDevices',
    'toUpdateDTO',
  ])
  implements Readonly<LoginUserDTO> {}
