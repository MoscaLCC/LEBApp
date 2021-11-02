import * as dayjs from 'dayjs';
import { IRequest } from 'app/entities/request/request.model';
import { IPoint } from 'app/entities/point/point.model';

export interface IUserInfo {
  id?: number;
  phoneNumber?: string | null;
  nib?: string | null;
  nif?: number | null;
  birthday?: dayjs.Dayjs | null;
  address?: string | null;
  linkSocial?: string | null;
  numberRequests?: number | null;
  payedValue?: number | null;
  valueToPay?: number | null;
  ranking?: number | null;
  numberOfDeliveries?: number | null;
  numberOfKm?: number | null;
  requests?: IRequest[] | null;
  transportations?: IRequest[] | null;
  points?: IPoint[] | null;
}

export class UserInfo implements IUserInfo {
  constructor(
    public id?: number,
    public phoneNumber?: string | null,
    public nib?: string | null,
    public nif?: number | null,
    public birthday?: dayjs.Dayjs | null,
    public address?: string | null,
    public linkSocial?: string | null,
    public numberRequests?: number | null,
    public payedValue?: number | null,
    public valueToPay?: number | null,
    public ranking?: number | null,
    public numberOfDeliveries?: number | null,
    public numberOfKm?: number | null,
    public requests?: IRequest[] | null,
    public transportations?: IRequest[] | null,
    public points?: IPoint[] | null
  ) {}
}

export function getUserInfoIdentifier(userInfo: IUserInfo): number | undefined {
  return userInfo.id;
}
