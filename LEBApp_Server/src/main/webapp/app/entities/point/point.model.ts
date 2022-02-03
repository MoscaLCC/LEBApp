
export interface IPoint {
  id?: number;
  name?: string | null;
  openingTime?: string | null;
  closingTime?: string | null;
  address?: string | null;
  numberOfDeliveries?: number | null;
  status?: number | null;
  ownerPoint?: number | null;
}

export class Point implements IPoint {
  constructor(
    public id?: number,
    public name?: string | null,
    public openingTime?: string | null,
    public closingTime?: string | null,
    public address?: string | null,
    public numberOfDeliveries?: number | null,
    public status?: number | null,
    public ownerPoint?: number | null
  ) {}
}

export function getPointIdentifier(point: IPoint): number | undefined {
  return point.id;
}
