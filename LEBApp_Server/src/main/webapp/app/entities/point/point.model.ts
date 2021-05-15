import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { IDeliveryMan } from 'app/entities/delivery-man/delivery-man.model';
import { IZone } from 'app/entities/zone/zone.model';

export interface IPoint {
  id?: number;
  openingTime?: string | null;
  numberOfDeliveries?: number | null;
  receivedValue?: number | null;
  valueToReceive?: number | null;
  ranking?: number | null;
  userInfo?: IUserInfo;
  deliveryMen?: IDeliveryMan[] | null;
  zone?: IZone | null;
}

export class Point implements IPoint {
  constructor(
    public id?: number,
    public openingTime?: string | null,
    public numberOfDeliveries?: number | null,
    public receivedValue?: number | null,
    public valueToReceive?: number | null,
    public ranking?: number | null,
    public userInfo?: IUserInfo,
    public deliveryMen?: IDeliveryMan[] | null,
    public zone?: IZone | null
  ) {}
}

export function getPointIdentifier(point: IPoint): number | undefined {
  return point.id;
}
