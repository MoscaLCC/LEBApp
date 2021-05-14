jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRoute, Route } from '../route.model';
import { RouteService } from '../service/route.service';

import { RouteRoutingResolveService } from './route-routing-resolve.service';

describe('Service Tests', () => {
  describe('Route routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RouteRoutingResolveService;
    let service: RouteService;
    let resultRoute: IRoute | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RouteRoutingResolveService);
      service = TestBed.inject(RouteService);
      resultRoute = undefined;
    });

    describe('resolve', () => {
      it('should return IRoute returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRoute = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRoute).toEqual({ id: 123 });
      });

      it('should return new IRoute if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRoute = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRoute).toEqual(new Route());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRoute = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRoute).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
