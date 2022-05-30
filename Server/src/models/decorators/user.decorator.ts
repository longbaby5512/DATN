import { createParamDecorator, ExecutionContext } from '@nestjs/common';
import { UserDTO } from '../dto';
import RequestWithUser from '../interfaces/request-with-user.interface';

export const GetUser = createParamDecorator(
  (_data: unknown, context: ExecutionContext): UserDTO => {
    const request: RequestWithUser = context.switchToHttp().getRequest();
    return request.user;
  },
);
