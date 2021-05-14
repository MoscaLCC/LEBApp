import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRoute, getRouteIdentifier } from '../route.model';

export type EntityResponseType = HttpResponse<IRoute>;
export type EntityArrayResponseType = HttpResponse<IRoute[]>;

@Injectable({ providedIn: 'root' })
export class RouteService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/routes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(route: IRoute): Observable<EntityResponseType> {
    return this.http.post<IRoute>(this.resourceUrl, route, { observe: 'response' });
  }

  update(route: IRoute): Observable<EntityResponseType> {
    return this.http.put<IRoute>(`${this.resourceUrl}/${getRouteIdentifier(route) as number}`, route, { observe: 'response' });
  }

  partialUpdate(route: IRoute): Observable<EntityResponseType> {
    return this.http.patch<IRoute>(`${this.resourceUrl}/${getRouteIdentifier(route) as number}`, route, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRoute>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRoute[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRouteToCollectionIfMissing(routeCollection: IRoute[], ...routesToCheck: (IRoute | null | undefined)[]): IRoute[] {
    const routes: IRoute[] = routesToCheck.filter(isPresent);
    if (routes.length > 0) {
      const routeCollectionIdentifiers = routeCollection.map(routeItem => getRouteIdentifier(routeItem)!);
      const routesToAdd = routes.filter(routeItem => {
        const routeIdentifier = getRouteIdentifier(routeItem);
        if (routeIdentifier == null || routeCollectionIdentifiers.includes(routeIdentifier)) {
          return false;
        }
        routeCollectionIdentifiers.push(routeIdentifier);
        return true;
      });
      return [...routesToAdd, ...routeCollection];
    }
    return routeCollection;
  }
}
