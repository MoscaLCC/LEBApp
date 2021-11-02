jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UserInfoService } from '../service/user-info.service';
import { IUserInfo, UserInfo } from '../user-info.model';

import { UserInfoUpdateComponent } from './user-info-update.component';

describe('UserInfo Management Update Component', () => {
  let comp: UserInfoUpdateComponent;
  let fixture: ComponentFixture<UserInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userInfoService: UserInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserInfoUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(UserInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userInfoService = TestBed.inject(UserInfoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userInfo: IUserInfo = { id: 456 };

      activatedRoute.data = of({ userInfo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userInfo));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserInfo>>();
      const userInfo = { id: 123 };
      jest.spyOn(userInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userInfo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userInfoService.update).toHaveBeenCalledWith(userInfo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserInfo>>();
      const userInfo = new UserInfo();
      jest.spyOn(userInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userInfo }));
      saveSubject.complete();

      // THEN
      expect(userInfoService.create).toHaveBeenCalledWith(userInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserInfo>>();
      const userInfo = { id: 123 };
      jest.spyOn(userInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userInfoService.update).toHaveBeenCalledWith(userInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
