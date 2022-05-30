import { ConfigModule, ConfigService } from "@nestjs/config";
import { LoggerModuleAsyncParams, Params } from "nestjs-pino";
import { AuthController } from "../auth/auth.controller";
import { EcdhController } from "../ecdh/ecdh.controller";

export class PinoConfig {
    static getPinoConfig(configService: ConfigService): Params {
        return {
            pinoHttp: {
                prettyPrint: {
                    levelFirst: true,
                    translateTime: "yyyy-mm-dd HH:MM:ss",
                    colorize: true,
                }

            },
        }
    }
}

export const pinoConfig: LoggerModuleAsyncParams = {
    imports: [ConfigModule],
    inject: [ConfigService],
    useFactory: (configService: ConfigService) => {
        return PinoConfig.getPinoConfig(configService);
    },
}
