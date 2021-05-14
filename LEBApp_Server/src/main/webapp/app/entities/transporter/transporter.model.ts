import * as dayjs from 'dayjs';
import { IRidePath } from 'app/entities/ride-path/ride-path.model';
import { IZone } from 'app/entities/zone/zone.model';

export interface ITransporter {
  id?: number;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  nib?: string | null;
  nif?: number | null;
  birthday?: dayjs.Dayjs | null;
  address?: string | null;
  photo?: string | null;
  favouriteTransport?: string | null;
  numberOfDeliveries?: number | null;
  numberOfKm?: number | null;
  receivedValue?: number | null;
  valueToReceive?: number | null;
  ranking?: number | null;
  ridePaths?: IRidePath[] | null;
  zones?: IZone[] | null;
}

export class Transporter implements ITransporter {
  constructor(
    public id?: number,
    public name?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public nib?: string | null,
    public nif?: number | null,
    public birthday?: dayjs.Dayjs | null,
    public address?: string | null,
    public photo?: string | null,
    public favouriteTransport?: string | null,
    public numberOfDeliveries?: number | null,
    public numberOfKm?: number | null,
    public receivedValue?: number | null,
    public valueToReceive?: number | null,
    public ranking?: number | null,
    public ridePaths?: IRidePath[] | null,
    public zones?: IZone[] | null
  ) {}
}

export function getTransporterIdentifier(transporter: ITransporter): number | undefined {
  return transporter.id;
}
