import { TokenType } from '../enum';

export interface TokenCookie {
  type: TokenType;
  token: string;
  expires: number;
}
