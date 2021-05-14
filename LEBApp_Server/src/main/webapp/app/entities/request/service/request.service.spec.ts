import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Status } from 'app/entities/enumerations/status.model';
import { IRequest, Request } from '../request.model';

import { RequestService } from './request.service';

describe('Service Tests', () => {
  describe('Request Service', () => {
    let service: RequestService;
    let httpMock: HttpTestingController;
    let elemDefault: IRequest;
    let expectedResult: IRequest | IRequest[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RequestService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        productValue: 0,
        productName: 'AAAAAAA',
        source: 'AAAAAAA',
        destination: 'AAAAAAA',
        destinationContact: 'AAAAAAA',
        initDate: currentDate,
        expirationDate: currentDate,
        description: 'AAAAAAA',
        specialCharacteristics: 'AAAAAAA',
        productWeight: 0,
        status: Status.WAITING,
        estimatedDate: currentDate,
        deliveryTime: currentDate,
        shippingCosts: 0,
        rating: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            initDate: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedDate: currentDate.format(DATE_TIME_FORMAT),
            deliveryTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Request', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            initDate: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedDate: currentDate.format(DATE_TIME_FORMAT),
            deliveryTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            initDate: currentDate,
            expirationDate: currentDate,
            estimatedDate: currentDate,
            deliveryTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Request()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Request', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            productValue: 1,
            productName: 'BBBBBB',
            source: 'BBBBBB',
            destination: 'BBBBBB',
            destinationContact: 'BBBBBB',
            initDate: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            specialCharacteristics: 'BBBBBB',
            productWeight: 1,
            status: 'BBBBBB',
            estimatedDate: currentDate.format(DATE_TIME_FORMAT),
            deliveryTime: currentDate.format(DATE_TIME_FORMAT),
            shippingCosts: 1,
            rating: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            initDate: currentDate,
            expirationDate: currentDate,
            estimatedDate: currentDate,
            deliveryTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Request', () => {
        const patchObject = Object.assign(
          {
            destination: 'BBBBBB',
            initDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedDate: currentDate.format(DATE_TIME_FORMAT),
            shippingCosts: 1,
          },
          new Request()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            initDate: currentDate,
            expirationDate: currentDate,
            estimatedDate: currentDate,
            deliveryTime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Request', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            productValue: 1,
            productName: 'BBBBBB',
            source: 'BBBBBB',
            destination: 'BBBBBB',
            destinationContact: 'BBBBBB',
            initDate: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            specialCharacteristics: 'BBBBBB',
            productWeight: 1,
            status: 'BBBBBB',
            estimatedDate: currentDate.format(DATE_TIME_FORMAT),
            deliveryTime: currentDate.format(DATE_TIME_FORMAT),
            shippingCosts: 1,
            rating: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            initDate: currentDate,
            expirationDate: currentDate,
            estimatedDate: currentDate,
            deliveryTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Request', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRequestToCollectionIfMissing', () => {
        it('should add a Request to an empty array', () => {
          const request: IRequest = { id: 123 };
          expectedResult = service.addRequestToCollectionIfMissing([], request);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(request);
        });

        it('should not add a Request to an array that contains it', () => {
          const request: IRequest = { id: 123 };
          const requestCollection: IRequest[] = [
            {
              ...request,
            },
            { id: 456 },
          ];
          expectedResult = service.addRequestToCollectionIfMissing(requestCollection, request);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Request to an array that doesn't contain it", () => {
          const request: IRequest = { id: 123 };
          const requestCollection: IRequest[] = [{ id: 456 }];
          expectedResult = service.addRequestToCollectionIfMissing(requestCollection, request);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(request);
        });

        it('should add only unique Request to an array', () => {
          const requestArray: IRequest[] = [{ id: 123 }, { id: 456 }, { id: 61246 }];
          const requestCollection: IRequest[] = [{ id: 123 }];
          expectedResult = service.addRequestToCollectionIfMissing(requestCollection, ...requestArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const request: IRequest = { id: 123 };
          const request2: IRequest = { id: 456 };
          expectedResult = service.addRequestToCollectionIfMissing([], request, request2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(request);
          expect(expectedResult).toContain(request2);
        });

        it('should accept null and undefined values', () => {
          const request: IRequest = { id: 123 };
          expectedResult = service.addRequestToCollectionIfMissing([], null, request, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(request);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
