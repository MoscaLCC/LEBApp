import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRoute, Route } from '../route.model';

import { RouteService } from './route.service';

describe('Service Tests', () => {
  describe('Route Service', () => {
    let service: RouteService;
    let httpMock: HttpTestingController;
    let elemDefault: IRoute;
    let expectedResult: IRoute | IRoute[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RouteService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a Route', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Route()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Route', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Route', () => {
        const patchObject = Object.assign({}, new Route());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Route', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a Route', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRouteToCollectionIfMissing', () => {
        it('should add a Route to an empty array', () => {
          const route: IRoute = { id: 123 };
          expectedResult = service.addRouteToCollectionIfMissing([], route);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(route);
        });

        it('should not add a Route to an array that contains it', () => {
          const route: IRoute = { id: 123 };
          const routeCollection: IRoute[] = [
            {
              ...route,
            },
            { id: 456 },
          ];
          expectedResult = service.addRouteToCollectionIfMissing(routeCollection, route);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Route to an array that doesn't contain it", () => {
          const route: IRoute = { id: 123 };
          const routeCollection: IRoute[] = [{ id: 456 }];
          expectedResult = service.addRouteToCollectionIfMissing(routeCollection, route);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(route);
        });

        it('should add only unique Route to an array', () => {
          const routeArray: IRoute[] = [{ id: 123 }, { id: 456 }, { id: 5459 }];
          const routeCollection: IRoute[] = [{ id: 123 }];
          expectedResult = service.addRouteToCollectionIfMissing(routeCollection, ...routeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const route: IRoute = { id: 123 };
          const route2: IRoute = { id: 456 };
          expectedResult = service.addRouteToCollectionIfMissing([], route, route2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(route);
          expect(expectedResult).toContain(route2);
        });

        it('should accept null and undefined values', () => {
          const route: IRoute = { id: 123 };
          expectedResult = service.addRouteToCollectionIfMissing([], null, route, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(route);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
