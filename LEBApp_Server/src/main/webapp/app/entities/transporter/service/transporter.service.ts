import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransporter, getTransporterIdentifier } from '../transporter.model';

export type EntityResponseType = HttpResponse<ITransporter>;
export type EntityArrayResponseType = HttpResponse<ITransporter[]>;

@Injectable({ providedIn: 'root' })
export class TransporterService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/transporters');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(transporter: ITransporter): Observable<EntityResponseType> {
    return this.http.post<ITransporter>(this.resourceUrl, transporter, { observe: 'response' });
  }

  update(transporter: ITransporter): Observable<EntityResponseType> {
    return this.http.put<ITransporter>(`${this.resourceUrl}/${getTransporterIdentifier(transporter) as number}`, transporter, {
      observe: 'response',
    });
  }

  partialUpdate(transporter: ITransporter): Observable<EntityResponseType> {
    return this.http.patch<ITransporter>(`${this.resourceUrl}/${getTransporterIdentifier(transporter) as number}`, transporter, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransporter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransporter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransporterToCollectionIfMissing(
    transporterCollection: ITransporter[],
    ...transportersToCheck: (ITransporter | null | undefined)[]
  ): ITransporter[] {
    const transporters: ITransporter[] = transportersToCheck.filter(isPresent);
    if (transporters.length > 0) {
      const transporterCollectionIdentifiers = transporterCollection.map(transporterItem => getTransporterIdentifier(transporterItem)!);
      const transportersToAdd = transporters.filter(transporterItem => {
        const transporterIdentifier = getTransporterIdentifier(transporterItem);
        if (transporterIdentifier == null || transporterCollectionIdentifiers.includes(transporterIdentifier)) {
          return false;
        }
        transporterCollectionIdentifiers.push(transporterIdentifier);
        return true;
      });
      return [...transportersToAdd, ...transporterCollection];
    }
    return transporterCollection;
  }
}
