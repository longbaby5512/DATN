import { Body, Controller, Get, UseGuards } from '@nestjs/common';
import JwtAuthGuard from '../auth/jwt-auth.guard';
import { ECDHService } from '../security';

@Controller('ecdh')
export class EcdhController {
  @UseGuards(JwtAuthGuard)
  @Get('generate-keys')
  async generateKeys() {
    const ecdh = new ECDHService();
    const { publicKey, privateKey } = await ecdh.generateKeysAsync();
    return { publicKey, privateKey };
  }

  @UseGuards(JwtAuthGuard)
  @Get('compute-secret')
  async computeSecret(
    @Body('localPrivateKey') localPrivateKey: string,
    @Body('remotePublicKey') remotePublicKey: string,
  ) {
    const secret = await ECDHService.computeSecretAsync(
      localPrivateKey,
      remotePublicKey,
    );
    return secret;
  }
}
