import crypto, { ECDH } from 'crypto';

export class ECDHService {
  readonly ecdh: ECDH;

  constructor(mode = 'secp256k1') {
    this.ecdh = crypto.createECDH(mode);
  }

  generateKeys(): { publicKey: string; privateKey: string } {
    this.ecdh.generateKeys();
    return {
      publicKey: this.ecdh.getPublicKey().toString('hex'),
      privateKey: this.ecdh.getPrivateKey().toString('hex'),
    };
  }

  static computeSecret(
    privateKey: string,
    publicKey: string,
    mode = 'secp256k1',
  ): string {
    const ecdh = crypto.createECDH(mode);
    ecdh.setPrivateKey(Buffer.from(privateKey, 'hex'));
    const hash = crypto.createHash('sha256');
    hash.update(ecdh.computeSecret(Buffer.from(publicKey, 'hex')));
    return hash.digest('hex');
  }

  async generateKeysAsync(): Promise<{
    publicKey: string;
    privateKey: string;
  }> {
    this.ecdh.generateKeys();
    return {
      publicKey: this.ecdh.getPublicKey().toString('hex'),
      privateKey: this.ecdh.getPrivateKey().toString('hex'),
    };
  }

  static async computeSecretAsync(
    privateKey: string,
    publicKey: string,
    mode = 'secp256k1',
  ): Promise<string> {
    const ecdh = crypto.createECDH(mode);
    ecdh.setPrivateKey(Buffer.from(privateKey, 'hex'));
    const hash = crypto.createHash('sha256');
    hash.update(ecdh.computeSecret(Buffer.from(publicKey, 'hex')));
    return hash.digest('hex');
  }
}
