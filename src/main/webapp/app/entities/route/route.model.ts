export interface IRoute {
  id?: number;
}

export class Route implements IRoute {
  constructor(public id?: number) {}
}

export function getRouteIdentifier(route: IRoute): number | undefined {
  return route.id;
}
