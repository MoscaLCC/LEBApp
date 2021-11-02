export interface IRidePath {
  id?: number;
  source?: string | null;
  destination?: string | null;
  distance?: string | null;
  estimatedTime?: string | null;
  radius?: number | null;
}

export class RidePath implements IRidePath {
  constructor(
    public id?: number,
    public source?: string | null,
    public destination?: string | null,
    public distance?: string | null,
    public estimatedTime?: string | null,
    public radius?: number | null
  ) {}
}

export function getRidePathIdentifier(ridePath: IRidePath): number | undefined {
  return ridePath.id;
}
