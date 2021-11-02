import { IUserInfo } from 'app/entities/user-info/user-info.model';

export interface IPoint {
  id?: number;
  openingTime?: string | null;
  closingTime?: string | null;
  address?: string | null;
  numberOfDeliveries?: number | null;
  ownerPoint?: IUserInfo;
}

export class Point implements IPoint {
  constructor(
    public id?: number,
    public openingTime?: string | null,
    public closingTime?: string | null,
    public address?: string | null,
    public numberOfDeliveries?: number | null,
    public ownerPoint?: IUserInfo
  ) {}
}

export function getPointIdentifier(point: IPoint): number | undefined {
  return point.id;
}
