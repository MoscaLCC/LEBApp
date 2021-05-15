import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { IRequest } from 'app/entities/request/request.model';

export interface IProducer {
  id?: number;
  linkSocial?: string | null;
  numberRequests?: number | null;
  payedValue?: number | null;
  valueToPay?: number | null;
  ranking?: number | null;
  userInfo?: IUserInfo;
  requests?: IRequest[] | null;
}

export class Producer implements IProducer {
  constructor(
    public id?: number,
    public linkSocial?: string | null,
    public numberRequests?: number | null,
    public payedValue?: number | null,
    public valueToPay?: number | null,
    public ranking?: number | null,
    public userInfo?: IUserInfo,
    public requests?: IRequest[] | null
  ) {}
}

export function getProducerIdentifier(producer: IProducer): number | undefined {
  return producer.id;
}
