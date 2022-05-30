import { IBase } from './base.interface';

export interface IUser extends IBase {
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  salt: string;
}
