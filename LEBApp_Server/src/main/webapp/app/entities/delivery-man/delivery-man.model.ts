import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { IPoint } from 'app/entities/point/point.model';

export interface IDeliveryMan {
  id?: number;
  openingTime?: string | null;
  numberOfDeliveries?: number | null;
  numberOfKm?: number | null;
  receivedValue?: number | null;
  valueToReceive?: number | null;
  ranking?: number | null;
  userInfo?: IUserInfo;
  point?: IPoint;
}

export class DeliveryMan implements IDeliveryMan {
  constructor(
    public id?: number,
    public openingTime?: string | null,
    public numberOfDeliveries?: number | null,
    public numberOfKm?: number | null,
    public receivedValue?: number | null,
    public valueToReceive?: number | null,
    public ranking?: number | null,
    public userInfo?: IUserInfo,
    public point?: IPoint
  ) {}
}

export function getDeliveryManIdentifier(deliveryMan: IDeliveryMan): number | undefined {
  return deliveryMan.id;
}
