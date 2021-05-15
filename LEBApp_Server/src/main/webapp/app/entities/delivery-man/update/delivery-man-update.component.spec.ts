jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DeliveryManService } from '../service/delivery-man.service';
import { IDeliveryMan, DeliveryMan } from '../delivery-man.model';
import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { UserInfoService } from 'app/entities/user-info/service/user-info.service';
import { IPoint } from 'app/entities/point/point.model';
import { PointService } from 'app/entities/point/service/point.service';

import { DeliveryManUpdateComponent } from './delivery-man-update.component';

describe('Component Tests', () => {
  describe('DeliveryMan Management Update Component', () => {
    let comp: DeliveryManUpdateComponent;
    let fixture: ComponentFixture<DeliveryManUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let deliveryManService: DeliveryManService;
    let userInfoService: UserInfoService;
    let pointService: PointService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DeliveryManUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DeliveryManUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeliveryManUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      deliveryManService = TestBed.inject(DeliveryManService);
      userInfoService = TestBed.inject(UserInfoService);
      pointService = TestBed.inject(PointService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call userInfo query and add missing value', () => {
        const deliveryMan: IDeliveryMan = { id: 456 };
        const userInfo: IUserInfo = { id: 47036 };
        deliveryMan.userInfo = userInfo;

        const userInfoCollection: IUserInfo[] = [{ id: 72111 }];
        spyOn(userInfoService, 'query').and.returnValue(of(new HttpResponse({ body: userInfoCollection })));
        const expectedCollection: IUserInfo[] = [userInfo, ...userInfoCollection];
        spyOn(userInfoService, 'addUserInfoToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ deliveryMan });
        comp.ngOnInit();

        expect(userInfoService.query).toHaveBeenCalled();
        expect(userInfoService.addUserInfoToCollectionIfMissing).toHaveBeenCalledWith(userInfoCollection, userInfo);
        expect(comp.userInfosCollection).toEqual(expectedCollection);
      });

      it('Should call Point query and add missing value', () => {
        const deliveryMan: IDeliveryMan = { id: 456 };
        const point: IPoint = { id: 85131 };
        deliveryMan.point = point;

        const pointCollection: IPoint[] = [{ id: 35283 }];
        spyOn(pointService, 'query').and.returnValue(of(new HttpResponse({ body: pointCollection })));
        const additionalPoints = [point];
        const expectedCollection: IPoint[] = [...additionalPoints, ...pointCollection];
        spyOn(pointService, 'addPointToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ deliveryMan });
        comp.ngOnInit();

        expect(pointService.query).toHaveBeenCalled();
        expect(pointService.addPointToCollectionIfMissing).toHaveBeenCalledWith(pointCollection, ...additionalPoints);
        expect(comp.pointsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const deliveryMan: IDeliveryMan = { id: 456 };
        const userInfo: IUserInfo = { id: 43081 };
        deliveryMan.userInfo = userInfo;
        const point: IPoint = { id: 7196 };
        deliveryMan.point = point;

        activatedRoute.data = of({ deliveryMan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(deliveryMan));
        expect(comp.userInfosCollection).toContain(userInfo);
        expect(comp.pointsSharedCollection).toContain(point);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const deliveryMan = { id: 123 };
        spyOn(deliveryManService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ deliveryMan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: deliveryMan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(deliveryManService.update).toHaveBeenCalledWith(deliveryMan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const deliveryMan = new DeliveryMan();
        spyOn(deliveryManService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ deliveryMan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: deliveryMan }));
        saveSubject.complete();

        // THEN
        expect(deliveryManService.create).toHaveBeenCalledWith(deliveryMan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const deliveryMan = { id: 123 };
        spyOn(deliveryManService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ deliveryMan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(deliveryManService.update).toHaveBeenCalledWith(deliveryMan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserInfoById', () => {
        it('Should return tracked UserInfo primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserInfoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackPointById', () => {
        it('Should return tracked Point primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPointById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
