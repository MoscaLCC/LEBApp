import { IPoint } from 'app/entities/point/point.model';
import { ITransporter } from 'app/entities/transporter/transporter.model';

export interface IZone {
  id?: number;
  name?: string | null;
  points?: IPoint[] | null;
  transporters?: ITransporter[] | null;
}

export class Zone implements IZone {
  constructor(
    public id?: number,
    public name?: string | null,
    public points?: IPoint[] | null,
    public transporters?: ITransporter[] | null
  ) {}
}

export function getZoneIdentifier(zone: IZone): number | undefined {
  return zone.id;
}
