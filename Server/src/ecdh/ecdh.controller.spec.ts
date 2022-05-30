import { Test, TestingModule } from '@nestjs/testing';
import { EcdhController } from './ecdh.controller';

describe('EcdhController', () => {
  let controller: EcdhController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [EcdhController],
    }).compile();

    controller = module.get<EcdhController>(EcdhController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
