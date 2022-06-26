import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { User } from '../models/entities';
import { Repository } from 'typeorm';
import { CreateUserDTO, UpdatedUserDTO, UserDTO } from '../models/dto';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User) private readonly repo: Repository<User>,
  ) {}

  getByEmail = async (email: string): Promise<UserDTO> => {
    return await this.repo.findOne({ email }).then((user: User) => {
      return UserDTO.fromEntity(user);
    });
  };

  getById = async (id: string): Promise<UserDTO> => {
    return await this.repo.findOne({ id }).then((user: User) => {
      return UserDTO.fromEntity(user);
    });
  };

  getAll = async (start?: number, limit?: number): Promise<UserDTO[]> => {
    const users = await this.repo.find({
      skip: start,
      take: limit,
      order: {
        createdAt: 'DESC',
      },
    });
    return users.map(UserDTO.fromEntity);
  };

  create = async (createUserDTO: CreateUserDTO): Promise<UserDTO> => {
    const user = this.repo.create(createUserDTO);
    return await this.repo.save(user).then(UserDTO.fromEntity);
  };

  update = async (updatedUserDTO: UpdatedUserDTO) => {
    const user = this.repo.create(updatedUserDTO);
    return await this.repo.save(user).then(UserDTO.fromEntity);
  };
}
