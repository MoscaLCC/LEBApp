jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDimensions, Dimensions } from '../dimensions.model';
import { DimensionsService } from '../service/dimensions.service';

import { DimensionsRoutingResolveService } from './dimensions-routing-resolve.service';

describe('Service Tests', () => {
  describe('Dimensions routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DimensionsRoutingResolveService;
    let service: DimensionsService;
    let resultDimensions: IDimensions | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DimensionsRoutingResolveService);
      service = TestBed.inject(DimensionsService);
      resultDimensions = undefined;
    });

    describe('resolve', () => {
      it('should return IDimensions returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDimensions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDimensions).toEqual({ id: 123 });
      });

      it('should return new IDimensions if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDimensions = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDimensions).toEqual(new Dimensions());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDimensions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDimensions).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
