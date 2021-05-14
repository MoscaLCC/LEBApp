import { IDeliveryMan } from 'app/entities/delivery-man/delivery-man.model';
import { IZone } from 'app/entities/zone/zone.model';

export interface IPoint {
  id?: number;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  nib?: string | null;
  nif?: number | null;
  address?: string | null;
  openingTime?: string | null;
  numberOfDeliveries?: number | null;
  receivedValue?: number | null;
  valueToReceive?: number | null;
  ranking?: number | null;
  deliveryMen?: IDeliveryMan[] | null;
  zone?: IZone | null;
}

export class Point implements IPoint {
  constructor(
    public id?: number,
    public name?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public nib?: string | null,
    public nif?: number | null,
    public address?: string | null,
    public openingTime?: string | null,
    public numberOfDeliveries?: number | null,
    public receivedValue?: number | null,
    public valueToReceive?: number | null,
    public ranking?: number | null,
    public deliveryMen?: IDeliveryMan[] | null,
    public zone?: IZone | null
  ) {}
}

export function getPointIdentifier(point: IPoint): number | undefined {
  return point.id;
}
