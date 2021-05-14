import { IRequest } from 'app/entities/request/request.model';
import { ITransporter } from 'app/entities/transporter/transporter.model';

export interface IRidePath {
  id?: number;
  source?: string | null;
  destination?: string | null;
  distance?: string | null;
  estimatedTime?: string | null;
  requests?: IRequest[] | null;
  transports?: ITransporter[] | null;
}

export class RidePath implements IRidePath {
  constructor(
    public id?: number,
    public source?: string | null,
    public destination?: string | null,
    public distance?: string | null,
    public estimatedTime?: string | null,
    public requests?: IRequest[] | null,
    public transports?: ITransporter[] | null
  ) {}
}

export function getRidePathIdentifier(ridePath: IRidePath): number | undefined {
  return ridePath.id;
}
