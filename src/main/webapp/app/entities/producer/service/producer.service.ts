import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProducer, getProducerIdentifier } from '../producer.model';

export type EntityResponseType = HttpResponse<IProducer>;
export type EntityArrayResponseType = HttpResponse<IProducer[]>;

@Injectable({ providedIn: 'root' })
export class ProducerService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/producers');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(producer: IProducer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producer);
    return this.http
      .post<IProducer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(producer: IProducer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producer);
    return this.http
      .put<IProducer>(`${this.resourceUrl}/${getProducerIdentifier(producer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(producer: IProducer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producer);
    return this.http
      .patch<IProducer>(`${this.resourceUrl}/${getProducerIdentifier(producer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProducer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProducer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProducerToCollectionIfMissing(producerCollection: IProducer[], ...producersToCheck: (IProducer | null | undefined)[]): IProducer[] {
    const producers: IProducer[] = producersToCheck.filter(isPresent);
    if (producers.length > 0) {
      const producerCollectionIdentifiers = producerCollection.map(producerItem => getProducerIdentifier(producerItem)!);
      const producersToAdd = producers.filter(producerItem => {
        const producerIdentifier = getProducerIdentifier(producerItem);
        if (producerIdentifier == null || producerCollectionIdentifiers.includes(producerIdentifier)) {
          return false;
        }
        producerCollectionIdentifiers.push(producerIdentifier);
        return true;
      });
      return [...producersToAdd, ...producerCollection];
    }
    return producerCollection;
  }

  protected convertDateFromClient(producer: IProducer): IProducer {
    return Object.assign({}, producer, {
      birthday: producer.birthday?.isValid() ? producer.birthday.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((producer: IProducer) => {
        producer.birthday = producer.birthday ? dayjs(producer.birthday) : undefined;
      });
    }
    return res;
  }
}
