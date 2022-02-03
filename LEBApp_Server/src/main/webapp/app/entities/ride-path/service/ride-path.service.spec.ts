import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRidePath, RidePath } from '../ride-path.model';

import { RidePathService } from './ride-path.service';

describe('RidePath Service', () => {
  let service: RidePathService;
  let httpMock: HttpTestingController;
  let elemDefault: IRidePath;
  let expectedResult: IRidePath | IRidePath[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RidePathService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      source: 'AAAAAAA',
      destination: 'AAAAAAA',
      distance: 'AAAAAAA',
      estimatedTime: 'AAAAAAA',
      radius: 0,
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

    it('should create a RidePath', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new RidePath()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RidePath', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          source: 'BBBBBB',
          destination: 'BBBBBB',
          distance: 'BBBBBB',
          estimatedTime: 'BBBBBB',
          radius: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RidePath', () => {
      const patchObject = Object.assign(
        {
          source: 'BBBBBB',
          estimatedTime: 'BBBBBB',
          radius: 1,
        },
        new RidePath()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RidePath', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          source: 'BBBBBB',
          destination: 'BBBBBB',
          distance: 'BBBBBB',
          estimatedTime: 'BBBBBB',
          radius: 1,
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

    it('should delete a RidePath', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRidePathToCollectionIfMissing', () => {
      it('should add a RidePath to an empty array', () => {
        const ridePath: IRidePath = { id: 123 };
        expectedResult = service.addRidePathToCollectionIfMissing([], ridePath);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ridePath);
      });

      it('should not add a RidePath to an array that contains it', () => {
        const ridePath: IRidePath = { id: 123 };
        const ridePathCollection: IRidePath[] = [
          {
            ...ridePath,
          },
          { id: 456 },
        ];
        expectedResult = service.addRidePathToCollectionIfMissing(ridePathCollection, ridePath);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RidePath to an array that doesn't contain it", () => {
        const ridePath: IRidePath = { id: 123 };
        const ridePathCollection: IRidePath[] = [{ id: 456 }];
        expectedResult = service.addRidePathToCollectionIfMissing(ridePathCollection, ridePath);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ridePath);
      });

      it('should add only unique RidePath to an array', () => {
        const ridePathArray: IRidePath[] = [{ id: 123 }, { id: 456 }, { id: 65223 }];
        const ridePathCollection: IRidePath[] = [{ id: 123 }];
        expectedResult = service.addRidePathToCollectionIfMissing(ridePathCollection, ...ridePathArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ridePath: IRidePath = { id: 123 };
        const ridePath2: IRidePath = { id: 456 };
        expectedResult = service.addRidePathToCollectionIfMissing([], ridePath, ridePath2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ridePath);
        expect(expectedResult).toContain(ridePath2);
      });

      it('should accept null and undefined values', () => {
        const ridePath: IRidePath = { id: 123 };
        expectedResult = service.addRidePathToCollectionIfMissing([], null, ridePath, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ridePath);
      });

      it('should return initial array if no RidePath is added', () => {
        const ridePathCollection: IRidePath[] = [{ id: 123 }];
        expectedResult = service.addRidePathToCollectionIfMissing(ridePathCollection, undefined, null);
        expect(expectedResult).toEqual(ridePathCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
