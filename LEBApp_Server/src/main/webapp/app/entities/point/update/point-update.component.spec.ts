jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PointService } from '../service/point.service';
import { IPoint, Point } from '../point.model';
import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { UserInfoService } from 'app/entities/user-info/service/user-info.service';

import { PointUpdateComponent } from './point-update.component';

describe('Point Management Update Component', () => {
  let comp: PointUpdateComponent;
  let fixture: ComponentFixture<PointUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointService: PointService;
  let userInfoService: UserInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PointUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PointUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointService = TestBed.inject(PointService);
    userInfoService = TestBed.inject(UserInfoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call UserInfo query and add missing value', () => {
      const point: IPoint = { id: 456 };
      const ownerPoint: IUserInfo = { id: 45174 };
      point.ownerPoint = ownerPoint;

      const userInfoCollection: IUserInfo[] = [{ id: 92853 }];
      jest.spyOn(userInfoService, 'query').mockReturnValue(of(new HttpResponse({ body: userInfoCollection })));
      const additionalUserInfos = [ownerPoint];
      const expectedCollection: IUserInfo[] = [...additionalUserInfos, ...userInfoCollection];
      jest.spyOn(userInfoService, 'addUserInfoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ point });
      comp.ngOnInit();

      expect(userInfoService.query).toHaveBeenCalled();
      expect(userInfoService.addUserInfoToCollectionIfMissing).toHaveBeenCalledWith(userInfoCollection, ...additionalUserInfos);
      expect(comp.userInfosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const point: IPoint = { id: 456 };
      const ownerPoint: IUserInfo = { id: 80629 };
      point.ownerPoint = ownerPoint;

      activatedRoute.data = of({ point });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(point));
      expect(comp.userInfosSharedCollection).toContain(ownerPoint);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Point>>();
      const point = { id: 123 };
      jest.spyOn(pointService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ point });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: point }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointService.update).toHaveBeenCalledWith(point);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Point>>();
      const point = new Point();
      jest.spyOn(pointService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ point });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: point }));
      saveSubject.complete();

      // THEN
      expect(pointService.create).toHaveBeenCalledWith(point);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Point>>();
      const point = { id: 123 };
      jest.spyOn(pointService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ point });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointService.update).toHaveBeenCalledWith(point);
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
  });
});
