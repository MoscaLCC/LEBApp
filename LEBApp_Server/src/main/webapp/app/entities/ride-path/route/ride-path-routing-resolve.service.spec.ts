jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRidePath, RidePath } from '../ride-path.model';
import { RidePathService } from '../service/ride-path.service';

import { RidePathRoutingResolveService } from './ride-path-routing-resolve.service';

describe('Service Tests', () => {
  describe('RidePath routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RidePathRoutingResolveService;
    let service: RidePathService;
    let resultRidePath: IRidePath | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RidePathRoutingResolveService);
      service = TestBed.inject(RidePathService);
      resultRidePath = undefined;
    });

    describe('resolve', () => {
      it('should return IRidePath returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRidePath = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRidePath).toEqual({ id: 123 });
      });

      it('should return new IRidePath if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRidePath = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRidePath).toEqual(new RidePath());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRidePath = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRidePath).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
