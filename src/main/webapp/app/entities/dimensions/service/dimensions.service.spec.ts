import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDimensions, Dimensions } from '../dimensions.model';

import { DimensionsService } from './dimensions.service';

describe('Service Tests', () => {
  describe('Dimensions Service', () => {
    let service: DimensionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDimensions;
    let expectedResult: IDimensions | IDimensions[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DimensionsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        height: 0,
        width: 0,
        depth: 0,
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

      it('should create a Dimensions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Dimensions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Dimensions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            height: 1,
            width: 1,
            depth: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Dimensions', () => {
        const patchObject = Object.assign(
          {
            height: 1,
            depth: 1,
          },
          new Dimensions()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Dimensions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            height: 1,
            width: 1,
            depth: 1,
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

      it('should delete a Dimensions', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDimensionsToCollectionIfMissing', () => {
        it('should add a Dimensions to an empty array', () => {
          const dimensions: IDimensions = { id: 123 };
          expectedResult = service.addDimensionsToCollectionIfMissing([], dimensions);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dimensions);
        });

        it('should not add a Dimensions to an array that contains it', () => {
          const dimensions: IDimensions = { id: 123 };
          const dimensionsCollection: IDimensions[] = [
            {
              ...dimensions,
            },
            { id: 456 },
          ];
          expectedResult = service.addDimensionsToCollectionIfMissing(dimensionsCollection, dimensions);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Dimensions to an array that doesn't contain it", () => {
          const dimensions: IDimensions = { id: 123 };
          const dimensionsCollection: IDimensions[] = [{ id: 456 }];
          expectedResult = service.addDimensionsToCollectionIfMissing(dimensionsCollection, dimensions);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dimensions);
        });

        it('should add only unique Dimensions to an array', () => {
          const dimensionsArray: IDimensions[] = [{ id: 123 }, { id: 456 }, { id: 88928 }];
          const dimensionsCollection: IDimensions[] = [{ id: 123 }];
          expectedResult = service.addDimensionsToCollectionIfMissing(dimensionsCollection, ...dimensionsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dimensions: IDimensions = { id: 123 };
          const dimensions2: IDimensions = { id: 456 };
          expectedResult = service.addDimensionsToCollectionIfMissing([], dimensions, dimensions2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dimensions);
          expect(expectedResult).toContain(dimensions2);
        });

        it('should accept null and undefined values', () => {
          const dimensions: IDimensions = { id: 123 };
          expectedResult = service.addDimensionsToCollectionIfMissing([], null, dimensions, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dimensions);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
