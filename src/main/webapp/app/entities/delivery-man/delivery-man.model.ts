import * as dayjs from 'dayjs';
import { IPoint } from 'app/entities/point/point.model';

export interface IDeliveryMan {
  id?: number;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  nif?: number | null;
  nib?: string | null;
  birthday?: dayjs.Dayjs | null;
  address?: string | null;
  photo?: string | null;
  openingTime?: string | null;
  numberOfDeliveries?: number | null;
  numberOfKm?: number | null;
  receivedValue?: number | null;
  valueToReceive?: number | null;
  ranking?: number | null;
  point?: IPoint;
}

export class DeliveryMan implements IDeliveryMan {
  constructor(
    public id?: number,
    public name?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public nif?: number | null,
    public nib?: string | null,
    public birthday?: dayjs.Dayjs | null,
    public address?: string | null,
    public photo?: string | null,
    public openingTime?: string | null,
    public numberOfDeliveries?: number | null,
    public numberOfKm?: number | null,
    public receivedValue?: number | null,
    public valueToReceive?: number | null,
    public ranking?: number | null,
    public point?: IPoint
  ) {}
}

export function getDeliveryManIdentifier(deliveryMan: IDeliveryMan): number | undefined {
  return deliveryMan.id;
}
