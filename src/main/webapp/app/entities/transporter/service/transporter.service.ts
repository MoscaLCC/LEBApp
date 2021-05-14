import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
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
    const copy = this.convertDateFromClient(transporter);
    return this.http
      .post<ITransporter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transporter: ITransporter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transporter);
    return this.http
      .put<ITransporter>(`${this.resourceUrl}/${getTransporterIdentifier(transporter) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transporter: ITransporter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transporter);
    return this.http
      .patch<ITransporter>(`${this.resourceUrl}/${getTransporterIdentifier(transporter) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransporter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransporter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
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

  protected convertDateFromClient(transporter: ITransporter): ITransporter {
    return Object.assign({}, transporter, {
      birthday: transporter.birthday?.isValid() ? transporter.birthday.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthday = res.body.birthday ? dayjs(res.body.birthday) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transporter: ITransporter) => {
        transporter.birthday = transporter.birthday ? dayjs(transporter.birthday) : undefined;
      });
    }
    return res;
  }
}
