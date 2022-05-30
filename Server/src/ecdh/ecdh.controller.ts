import { Body, Controller, Get, UseGuards } from '@nestjs/common';
import JwtAuthGuard from '../auth/jwt-auth.guard';
import { ECDHService } from '../security';
import snakecaseKeys from 'snakecase-keys';

@Controller('ecdh')
export class EcdhController {
  @UseGuards(JwtAuthGuard)
  @Get('generate-keys')
  async generateKeys() {
    const ecdh = new ECDHService();
    const { publicKey, privateKey } = await ecdh.generateKeysAsync();
    return snakecaseKeys({ publicKey, privateKey });
  }

  @UseGuards(JwtAuthGuard)
  @Get('compute-secret')
  async computeSecret(
    @Body('local_private_key') localPrivateKey: string,
    @Body('remote_public_key') remotePublicKey: string,
  ) {
    const secret = await ECDHService.computeSecretAsync(localPrivateKey, remotePublicKey);
    return snakecaseKeys({ secret });
  }
}
