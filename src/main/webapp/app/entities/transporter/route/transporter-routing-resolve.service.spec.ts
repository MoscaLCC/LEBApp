jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITransporter, Transporter } from '../transporter.model';
import { TransporterService } from '../service/transporter.service';

import { TransporterRoutingResolveService } from './transporter-routing-resolve.service';

describe('Service Tests', () => {
  describe('Transporter routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TransporterRoutingResolveService;
    let service: TransporterService;
    let resultTransporter: ITransporter | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TransporterRoutingResolveService);
      service = TestBed.inject(TransporterService);
      resultTransporter = undefined;
    });

    describe('resolve', () => {
      it('should return ITransporter returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTransporter = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTransporter).toEqual({ id: 123 });
      });

      it('should return new ITransporter if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTransporter = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTransporter).toEqual(new Transporter());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTransporter = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTransporter).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
