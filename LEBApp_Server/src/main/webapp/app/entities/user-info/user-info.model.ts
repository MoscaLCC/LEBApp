import * as dayjs from 'dayjs';

export interface IUserInfo {
  id?: number;
  phoneNumber?: string | null;
  nib?: string | null;
  nif?: number | null;
  birthday?: dayjs.Dayjs | null;
  adress?: string | null;
}

export class UserInfo implements IUserInfo {
  constructor(
    public id?: number,
    public phoneNumber?: string | null,
    public nib?: string | null,
    public nif?: number | null,
    public birthday?: dayjs.Dayjs | null,
    public adress?: string | null
  ) {}
}

export function getUserInfoIdentifier(userInfo: IUserInfo): number | undefined {
  return userInfo.id;
}
