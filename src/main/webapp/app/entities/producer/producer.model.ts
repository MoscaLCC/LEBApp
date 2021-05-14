import * as dayjs from 'dayjs';
import { IRequest } from 'app/entities/request/request.model';

export interface IProducer {
  id?: number;
  name?: string | null;
  mail?: string | null;
  phoneNumber?: string | null;
  nib?: string | null;
  nif?: number | null;
  birthday?: dayjs.Dayjs | null;
  adress?: string | null;
  photo?: string | null;
  linkSocial?: string | null;
  numberRequests?: number | null;
  payedValue?: number | null;
  valueToPay?: number | null;
  ranking?: number | null;
  requests?: IRequest[] | null;
}

export class Producer implements IProducer {
  constructor(
    public id?: number,
    public name?: string | null,
    public mail?: string | null,
    public phoneNumber?: string | null,
    public nib?: string | null,
    public nif?: number | null,
    public birthday?: dayjs.Dayjs | null,
    public adress?: string | null,
    public photo?: string | null,
    public linkSocial?: string | null,
    public numberRequests?: number | null,
    public payedValue?: number | null,
    public valueToPay?: number | null,
    public ranking?: number | null,
    public requests?: IRequest[] | null
  ) {}
}

export function getProducerIdentifier(producer: IProducer): number | undefined {
  return producer.id;
}
