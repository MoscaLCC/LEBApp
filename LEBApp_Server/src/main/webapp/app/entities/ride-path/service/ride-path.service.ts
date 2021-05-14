import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRidePath, getRidePathIdentifier } from '../ride-path.model';

export type EntityResponseType = HttpResponse<IRidePath>;
export type EntityArrayResponseType = HttpResponse<IRidePath[]>;

@Injectable({ providedIn: 'root' })
export class RidePathService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ride-paths');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(ridePath: IRidePath): Observable<EntityResponseType> {
    return this.http.post<IRidePath>(this.resourceUrl, ridePath, { observe: 'response' });
  }

  update(ridePath: IRidePath): Observable<EntityResponseType> {
    return this.http.put<IRidePath>(`${this.resourceUrl}/${getRidePathIdentifier(ridePath) as number}`, ridePath, { observe: 'response' });
  }

  partialUpdate(ridePath: IRidePath): Observable<EntityResponseType> {
    return this.http.patch<IRidePath>(`${this.resourceUrl}/${getRidePathIdentifier(ridePath) as number}`, ridePath, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRidePath>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRidePath[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRidePathToCollectionIfMissing(ridePathCollection: IRidePath[], ...ridePathsToCheck: (IRidePath | null | undefined)[]): IRidePath[] {
    const ridePaths: IRidePath[] = ridePathsToCheck.filter(isPresent);
    if (ridePaths.length > 0) {
      const ridePathCollectionIdentifiers = ridePathCollection.map(ridePathItem => getRidePathIdentifier(ridePathItem)!);
      const ridePathsToAdd = ridePaths.filter(ridePathItem => {
        const ridePathIdentifier = getRidePathIdentifier(ridePathItem);
        if (ridePathIdentifier == null || ridePathCollectionIdentifiers.includes(ridePathIdentifier)) {
          return false;
        }
        ridePathCollectionIdentifiers.push(ridePathIdentifier);
        return true;
      });
      return [...ridePathsToAdd, ...ridePathCollection];
    }
    return ridePathCollection;
  }
}
