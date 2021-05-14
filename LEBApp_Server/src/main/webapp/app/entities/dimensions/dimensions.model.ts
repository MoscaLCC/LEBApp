import { IRequest } from 'app/entities/request/request.model';

export interface IDimensions {
  id?: number;
  height?: number | null;
  width?: number | null;
  depth?: number | null;
  request?: IRequest | null;
}

export class Dimensions implements IDimensions {
  constructor(
    public id?: number,
    public height?: number | null,
    public width?: number | null,
    public depth?: number | null,
    public request?: IRequest | null
  ) {}
}

export function getDimensionsIdentifier(dimensions: IDimensions): number | undefined {
  return dimensions.id;
}
