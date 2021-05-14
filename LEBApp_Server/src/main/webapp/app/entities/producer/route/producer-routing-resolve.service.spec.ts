jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProducer, Producer } from '../producer.model';
import { ProducerService } from '../service/producer.service';

import { ProducerRoutingResolveService } from './producer-routing-resolve.service';

describe('Service Tests', () => {
  describe('Producer routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProducerRoutingResolveService;
    let service: ProducerService;
    let resultProducer: IProducer | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProducerRoutingResolveService);
      service = TestBed.inject(ProducerService);
      resultProducer = undefined;
    });

    describe('resolve', () => {
      it('should return IProducer returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProducer = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProducer).toEqual({ id: 123 });
      });

      it('should return new IProducer if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProducer = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProducer).toEqual(new Producer());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProducer = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProducer).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
