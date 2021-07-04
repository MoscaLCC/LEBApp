import * as dayjs from 'dayjs';

export interface IUserInfo {
  id?: number;
  phoneNumber?: string | null;
  nib?: string | null;
  nif?: number | null;
  birthday?: dayjs.Dayjs | null;
  address?: string | null;
}

export class UserInfo implements IUserInfo {
  constructor(
    public id?: number,
    public phoneNumber?: string | null,
    public nib?: string | null,
    public nif?: number | null,
    public birthday?: dayjs.Dayjs | null,
    public address?: string | null
  ) {}
}

export function getUserInfoIdentifier(userInfo: IUserInfo): number | undefined {
  return userInfo.id;
}
