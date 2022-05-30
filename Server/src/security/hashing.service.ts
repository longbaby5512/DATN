import crypto from 'crypto';
import argon2 from 'argon2';

export interface SaltedHash {
  hash: string;
  salt: string;
}

const hashingConfig = {
  type: argon2.argon2id,
  hashLength: 32,
  timeCost: 3,
  memoryCost: 4096,
  parallelism: 1,
};

export class HashingService {
  static hash = async (
    value: string,
    saltLength?: number,
  ): Promise<SaltedHash> => {
    if (saltLength === undefined) {
      saltLength = 32;
    }
    const salt = crypto.randomBytes(saltLength);
    const hexSalt = salt.toString('hex');
    const hash = await argon2.hash(value + hexSalt, {
      ...hashingConfig,
      salt,
    });
    return { hash, salt: hexSalt };
  };

  static compare = async (
    value: string,
    saltedHash: SaltedHash,
  ): Promise<boolean> => {
    const { hash, salt } = saltedHash;
    return await argon2.verify(hash, value + salt, hashingConfig);
  };
}
