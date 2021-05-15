import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { IRidePath } from 'app/entities/ride-path/ride-path.model';
import { IZone } from 'app/entities/zone/zone.model';

export interface ITransporter {
  id?: number;
  favouriteTransport?: string | null;
  numberOfDeliveries?: number | null;
  numberOfKm?: number | null;
  receivedValue?: number | null;
  valueToReceive?: number | null;
  ranking?: number | null;
  userInfo?: IUserInfo;
  ridePaths?: IRidePath[] | null;
  zones?: IZone[] | null;
}

export class Transporter implements ITransporter {
  constructor(
    public id?: number,
    public favouriteTransport?: string | null,
    public numberOfDeliveries?: number | null,
    public numberOfKm?: number | null,
    public receivedValue?: number | null,
    public valueToReceive?: number | null,
    public ranking?: number | null,
    public userInfo?: IUserInfo,
    public ridePaths?: IRidePath[] | null,
    public zones?: IZone[] | null
  ) {}
}

export function getTransporterIdentifier(transporter: ITransporter): number | undefined {
  return transporter.id;
}
