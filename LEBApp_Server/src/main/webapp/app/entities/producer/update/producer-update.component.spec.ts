jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProducerService } from '../service/producer.service';
import { IProducer, Producer } from '../producer.model';
import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { UserInfoService } from 'app/entities/user-info/service/user-info.service';

import { ProducerUpdateComponent } from './producer-update.component';

describe('Component Tests', () => {
  describe('Producer Management Update Component', () => {
    let comp: ProducerUpdateComponent;
    let fixture: ComponentFixture<ProducerUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let producerService: ProducerService;
    let userInfoService: UserInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProducerUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProducerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProducerUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      producerService = TestBed.inject(ProducerService);
      userInfoService = TestBed.inject(UserInfoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call userInfo query and add missing value', () => {
        const producer: IProducer = { id: 456 };
        const userInfo: IUserInfo = { id: 37646 };
        producer.userInfo = userInfo;

        const userInfoCollection: IUserInfo[] = [{ id: 88884 }];
        spyOn(userInfoService, 'query').and.returnValue(of(new HttpResponse({ body: userInfoCollection })));
        const expectedCollection: IUserInfo[] = [userInfo, ...userInfoCollection];
        spyOn(userInfoService, 'addUserInfoToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ producer });
        comp.ngOnInit();

        expect(userInfoService.query).toHaveBeenCalled();
        expect(userInfoService.addUserInfoToCollectionIfMissing).toHaveBeenCalledWith(userInfoCollection, userInfo);
        expect(comp.userInfosCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const producer: IProducer = { id: 456 };
        const userInfo: IUserInfo = { id: 80089 };
        producer.userInfo = userInfo;

        activatedRoute.data = of({ producer });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(producer));
        expect(comp.userInfosCollection).toContain(userInfo);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const producer = { id: 123 };
        spyOn(producerService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ producer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: producer }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(producerService.update).toHaveBeenCalledWith(producer);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const producer = new Producer();
        spyOn(producerService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ producer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: producer }));
        saveSubject.complete();

        // THEN
        expect(producerService.create).toHaveBeenCalledWith(producer);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const producer = { id: 123 };
        spyOn(producerService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ producer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(producerService.update).toHaveBeenCalledWith(producer);
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
});
