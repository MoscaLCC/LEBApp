import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDimensions, getDimensionsIdentifier } from '../dimensions.model';

export type EntityResponseType = HttpResponse<IDimensions>;
export type EntityArrayResponseType = HttpResponse<IDimensions[]>;

@Injectable({ providedIn: 'root' })
export class DimensionsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/dimensions');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dimensions: IDimensions): Observable<EntityResponseType> {
    return this.http.post<IDimensions>(this.resourceUrl, dimensions, { observe: 'response' });
  }

  update(dimensions: IDimensions): Observable<EntityResponseType> {
    return this.http.put<IDimensions>(`${this.resourceUrl}/${getDimensionsIdentifier(dimensions) as number}`, dimensions, {
      observe: 'response',
    });
  }

  partialUpdate(dimensions: IDimensions): Observable<EntityResponseType> {
    return this.http.patch<IDimensions>(`${this.resourceUrl}/${getDimensionsIdentifier(dimensions) as number}`, dimensions, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDimensions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDimensions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDimensionsToCollectionIfMissing(
    dimensionsCollection: IDimensions[],
    ...dimensionsToCheck: (IDimensions | null | undefined)[]
  ): IDimensions[] {
    const dimensions: IDimensions[] = dimensionsToCheck.filter(isPresent);
    if (dimensions.length > 0) {
      const dimensionsCollectionIdentifiers = dimensionsCollection.map(dimensionsItem => getDimensionsIdentifier(dimensionsItem)!);
      const dimensionsToAdd = dimensions.filter(dimensionsItem => {
        const dimensionsIdentifier = getDimensionsIdentifier(dimensionsItem);
        if (dimensionsIdentifier == null || dimensionsCollectionIdentifiers.includes(dimensionsIdentifier)) {
          return false;
        }
        dimensionsCollectionIdentifiers.push(dimensionsIdentifier);
        return true;
      });
      return [...dimensionsToAdd, ...dimensionsCollection];
    }
    return dimensionsCollection;
  }
}
