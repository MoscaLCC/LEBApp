import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITransporter, Transporter } from '../transporter.model';

import { TransporterService } from './transporter.service';

describe('Service Tests', () => {
  describe('Transporter Service', () => {
    let service: TransporterService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransporter;
    let expectedResult: ITransporter | ITransporter[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TransporterService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        favouriteTransport: 'AAAAAAA',
        numberOfDeliveries: 0,
        numberOfKm: 0,
        receivedValue: 0,
        valueToReceive: 0,
        ranking: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Transporter', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Transporter()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Transporter', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            favouriteTransport: 'BBBBBB',
            numberOfDeliveries: 1,
            numberOfKm: 1,
            receivedValue: 1,
            valueToReceive: 1,
            ranking: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Transporter', () => {
        const patchObject = Object.assign(
          {
            favouriteTransport: 'BBBBBB',
            numberOfKm: 1,
          },
          new Transporter()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Transporter', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            favouriteTransport: 'BBBBBB',
            numberOfDeliveries: 1,
            numberOfKm: 1,
            receivedValue: 1,
            valueToReceive: 1,
            ranking: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Transporter', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTransporterToCollectionIfMissing', () => {
        it('should add a Transporter to an empty array', () => {
          const transporter: ITransporter = { id: 123 };
          expectedResult = service.addTransporterToCollectionIfMissing([], transporter);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(transporter);
        });

        it('should not add a Transporter to an array that contains it', () => {
          const transporter: ITransporter = { id: 123 };
          const transporterCollection: ITransporter[] = [
            {
              ...transporter,
            },
            { id: 456 },
          ];
          expectedResult = service.addTransporterToCollectionIfMissing(transporterCollection, transporter);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Transporter to an array that doesn't contain it", () => {
          const transporter: ITransporter = { id: 123 };
          const transporterCollection: ITransporter[] = [{ id: 456 }];
          expectedResult = service.addTransporterToCollectionIfMissing(transporterCollection, transporter);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(transporter);
        });

        it('should add only unique Transporter to an array', () => {
          const transporterArray: ITransporter[] = [{ id: 123 }, { id: 456 }, { id: 41462 }];
          const transporterCollection: ITransporter[] = [{ id: 123 }];
          expectedResult = service.addTransporterToCollectionIfMissing(transporterCollection, ...transporterArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const transporter: ITransporter = { id: 123 };
          const transporter2: ITransporter = { id: 456 };
          expectedResult = service.addTransporterToCollectionIfMissing([], transporter, transporter2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(transporter);
          expect(expectedResult).toContain(transporter2);
        });

        it('should accept null and undefined values', () => {
          const transporter: ITransporter = { id: 123 };
          expectedResult = service.addTransporterToCollectionIfMissing([], null, transporter, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(transporter);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
