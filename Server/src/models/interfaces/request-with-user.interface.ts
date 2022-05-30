import { Request } from 'express';
import { UserDTO } from '../dto';

export default interface RequestWithUser extends Request {
  user: UserDTO;
}
