import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeliveryMan, getDeliveryManIdentifier } from '../delivery-man.model';

export type EntityResponseType = HttpResponse<IDeliveryMan>;
export type EntityArrayResponseType = HttpResponse<IDeliveryMan[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryManService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/delivery-men');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(deliveryMan: IDeliveryMan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryMan);
    return this.http
      .post<IDeliveryMan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deliveryMan: IDeliveryMan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryMan);
    return this.http
      .put<IDeliveryMan>(`${this.resourceUrl}/${getDeliveryManIdentifier(deliveryMan) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(deliveryMan: IDeliveryMan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryMan);
    return this.http
      .patch<IDeliveryMan>(`${this.resourceUrl}/${getDeliveryManIdentifier(deliveryMan) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeliveryMan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeliveryMan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDeliveryManToCollectionIfMissing(
    deliveryManCollection: IDeliveryMan[],
    ...deliveryMenToCheck: (IDeliveryMan | null | undefined)[]
  ): IDeliveryMan[] {
    const deliveryMen: IDeliveryMan[] = deliveryMenToCheck.filter(isPresent);
    if (deliveryMen.length > 0) {
      const deliveryManCollectionIdentifiers = deliveryManCollection.map(deliveryManItem => getDeliveryManIdentifier(deliveryManItem)!);
      const deliveryMenToAdd = deliveryMen.filter(deliveryManItem => {
        const deliveryManIdentifier = getDeliveryManIdentifier(deliveryManItem);
        if (deliveryManIdentifier == null || deliveryManCollectionIdentifiers.includes(deliveryManIdentifier)) {
          return false;
        }
        deliveryManCollectionIdentifiers.push(deliveryManIdentifier);
        return true;
      });
      return [...deliveryMenToAdd, ...deliveryManCollection];
    }
    return deliveryManCollection;
  }

  protected convertDateFromClient(deliveryMan: IDeliveryMan): IDeliveryMan {
    return Object.assign({}, deliveryMan, {
      birthday: deliveryMan.birthday?.isValid() ? deliveryMan.birthday.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((deliveryMan: IDeliveryMan) => {
        deliveryMan.birthday = deliveryMan.birthday ? dayjs(deliveryMan.birthday) : undefined;
      });
    }
    return res;
  }
}
