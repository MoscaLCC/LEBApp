import { Status } from 'app/entities/enumerations/status.model';

export interface IRequest {
  id?: number;
  productValue?: number | null;
  productName?: string | null;
  source?: string | null;
  destination?: string | null;
  destinationContactEmail?: string | null;
  destinationContactMobile?: string | null;
  initDate?: string | null;
  expirationDate?: string | null;
  specialCharacteristics?: string | null;
  weight?: number | null;
  hight?: number | null;
  width?: number | null;
  status?: Status | null;
  shippingCosts?: number | null;
  rating?: number | null;
  ownerRequest?: number | null;
  transporter?: number | null;
}

export class Request implements IRequest {
  constructor(
    public id?: number,
    public productValue?: number | null,
    public productName?: string | null,
    public source?: string | null,
    public destination?: string | null,
    public destinationContactEmail?: string | null,
    public destinationContactMobile?: string | null,
    public initDate?: string | null,
    public expirationDate?: string | null,
    public specialCharacteristics?: string | null,
    public weight?: number | null,
    public hight?: number | null,
    public width?: number | null,
    public status?: Status | null,
    public shippingCosts?: number | null,
    public rating?: number | null,
    public ownerRequest?: number | null,
    public transporter?: number | null
  ) {}
}

export function getRequestIdentifier(request: IRequest): number | undefined {
  return request.id;
}
