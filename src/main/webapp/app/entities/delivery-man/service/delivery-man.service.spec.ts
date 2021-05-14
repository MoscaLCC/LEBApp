import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDeliveryMan, DeliveryMan } from '../delivery-man.model';

import { DeliveryManService } from './delivery-man.service';

describe('Service Tests', () => {
  describe('DeliveryMan Service', () => {
    let service: DeliveryManService;
    let httpMock: HttpTestingController;
    let elemDefault: IDeliveryMan;
    let expectedResult: IDeliveryMan | IDeliveryMan[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DeliveryManService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        email: 'AAAAAAA',
        phoneNumber: 'AAAAAAA',
        nif: 0,
        nib: 'AAAAAAA',
        birthday: currentDate,
        address: 'AAAAAAA',
        photo: 'AAAAAAA',
        openingTime: 'AAAAAAA',
        numberOfDeliveries: 0,
        numberOfKm: 0,
        receivedValue: 0,
        valueToReceive: 0,
        ranking: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthday: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DeliveryMan', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthday: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthday: currentDate,
          },
          returnedFromService
        );

        service.create(new DeliveryMan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DeliveryMan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            nif: 1,
            nib: 'BBBBBB',
            birthday: currentDate.format(DATE_FORMAT),
            address: 'BBBBBB',
            photo: 'BBBBBB',
            openingTime: 'BBBBBB',
            numberOfDeliveries: 1,
            numberOfKm: 1,
            receivedValue: 1,
            valueToReceive: 1,
            ranking: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthday: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DeliveryMan', () => {
        const patchObject = Object.assign(
          {
            phoneNumber: 'BBBBBB',
            nif: 1,
            address: 'BBBBBB',
            openingTime: 'BBBBBB',
            numberOfKm: 1,
            receivedValue: 1,
          },
          new DeliveryMan()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            birthday: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DeliveryMan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            nif: 1,
            nib: 'BBBBBB',
            birthday: currentDate.format(DATE_FORMAT),
            address: 'BBBBBB',
            photo: 'BBBBBB',
            openingTime: 'BBBBBB',
            numberOfDeliveries: 1,
            numberOfKm: 1,
            receivedValue: 1,
            valueToReceive: 1,
            ranking: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthday: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DeliveryMan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDeliveryManToCollectionIfMissing', () => {
        it('should add a DeliveryMan to an empty array', () => {
          const deliveryMan: IDeliveryMan = { id: 123 };
          expectedResult = service.addDeliveryManToCollectionIfMissing([], deliveryMan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(deliveryMan);
        });

        it('should not add a DeliveryMan to an array that contains it', () => {
          const deliveryMan: IDeliveryMan = { id: 123 };
          const deliveryManCollection: IDeliveryMan[] = [
            {
              ...deliveryMan,
            },
            { id: 456 },
          ];
          expectedResult = service.addDeliveryManToCollectionIfMissing(deliveryManCollection, deliveryMan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DeliveryMan to an array that doesn't contain it", () => {
          const deliveryMan: IDeliveryMan = { id: 123 };
          const deliveryManCollection: IDeliveryMan[] = [{ id: 456 }];
          expectedResult = service.addDeliveryManToCollectionIfMissing(deliveryManCollection, deliveryMan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(deliveryMan);
        });

        it('should add only unique DeliveryMan to an array', () => {
          const deliveryManArray: IDeliveryMan[] = [{ id: 123 }, { id: 456 }, { id: 89240 }];
          const deliveryManCollection: IDeliveryMan[] = [{ id: 123 }];
          expectedResult = service.addDeliveryManToCollectionIfMissing(deliveryManCollection, ...deliveryManArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const deliveryMan: IDeliveryMan = { id: 123 };
          const deliveryMan2: IDeliveryMan = { id: 456 };
          expectedResult = service.addDeliveryManToCollectionIfMissing([], deliveryMan, deliveryMan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(deliveryMan);
          expect(expectedResult).toContain(deliveryMan2);
        });

        it('should accept null and undefined values', () => {
          const deliveryMan: IDeliveryMan = { id: 123 };
          expectedResult = service.addDeliveryManToCollectionIfMissing([], null, deliveryMan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(deliveryMan);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
