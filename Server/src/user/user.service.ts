import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { User } from '../models/entities';
import { CreateUserDTO, UserDTO } from '../models/dto';
import { Repository } from 'typeorm';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User) private readonly repo: Repository<User>,
  ) {}

  getByEmail = async (email: string): Promise<User> => {
    return await this.repo.findOne({ email });
  };

  getById = async (id: string): Promise<User> => {
    return await this.repo.findOne({ id });
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
    const user: User = createUserDTO.toEntity();
    return this.repo.save(user).then((user: User) => {
      return UserDTO.fromEntity(user);
    });
  };
}
