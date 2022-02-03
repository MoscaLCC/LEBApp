jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IUserInfo, UserInfo } from '../user-info.model';
import { UserInfoService } from '../service/user-info.service';

import { UserInfoRoutingResolveService } from './user-info-routing-resolve.service';

describe('UserInfo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UserInfoRoutingResolveService;
  let service: UserInfoService;
  let resultUserInfo: IUserInfo | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(UserInfoRoutingResolveService);
    service = TestBed.inject(UserInfoService);
    resultUserInfo = undefined;
  });

  describe('resolve', () => {
    it('should return IUserInfo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserInfo).toEqual({ id: 123 });
    });

    it('should return new IUserInfo if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserInfo = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUserInfo).toEqual(new UserInfo());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UserInfo })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserInfo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
