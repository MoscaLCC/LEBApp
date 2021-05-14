import * as dayjs from 'dayjs';
import { IDimensions } from 'app/entities/dimensions/dimensions.model';
import { IRidePath } from 'app/entities/ride-path/ride-path.model';
import { IProducer } from 'app/entities/producer/producer.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IRequest {
  id?: number;
  productValue?: number | null;
  productName?: string | null;
  source?: string | null;
  destination?: string | null;
  destinationContact?: string | null;
  initDate?: dayjs.Dayjs | null;
  expirationDate?: dayjs.Dayjs | null;
  description?: string | null;
  specialCharacteristics?: string | null;
  productWeight?: number | null;
  status?: Status | null;
  estimatedDate?: dayjs.Dayjs | null;
  deliveryTime?: dayjs.Dayjs | null;
  shippingCosts?: number | null;
  rating?: number | null;
  dimensions?: IDimensions | null;
  ridePath?: IRidePath;
  producer?: IProducer;
}

export class Request implements IRequest {
  constructor(
    public id?: number,
    public productValue?: number | null,
    public productName?: string | null,
    public source?: string | null,
    public destination?: string | null,
    public destinationContact?: string | null,
    public initDate?: dayjs.Dayjs | null,
    public expirationDate?: dayjs.Dayjs | null,
    public description?: string | null,
    public specialCharacteristics?: string | null,
    public productWeight?: number | null,
    public status?: Status | null,
    public estimatedDate?: dayjs.Dayjs | null,
    public deliveryTime?: dayjs.Dayjs | null,
    public shippingCosts?: number | null,
    public rating?: number | null,
    public dimensions?: IDimensions | null,
    public ridePath?: IRidePath,
    public producer?: IProducer
  ) {}
}

export function getRequestIdentifier(request: IRequest): number | undefined {
  return request.id;
}
