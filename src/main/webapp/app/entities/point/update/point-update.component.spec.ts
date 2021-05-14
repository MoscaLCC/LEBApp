jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PointService } from '../service/point.service';
import { IPoint, Point } from '../point.model';
import { IZone } from 'app/entities/zone/zone.model';
import { ZoneService } from 'app/entities/zone/service/zone.service';

import { PointUpdateComponent } from './point-update.component';

describe('Component Tests', () => {
  describe('Point Management Update Component', () => {
    let comp: PointUpdateComponent;
    let fixture: ComponentFixture<PointUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pointService: PointService;
    let zoneService: ZoneService;

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
      zoneService = TestBed.inject(ZoneService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Zone query and add missing value', () => {
        const point: IPoint = { id: 456 };
        const zone: IZone = { id: 26435 };
        point.zone = zone;

        const zoneCollection: IZone[] = [{ id: 80990 }];
        spyOn(zoneService, 'query').and.returnValue(of(new HttpResponse({ body: zoneCollection })));
        const additionalZones = [zone];
        const expectedCollection: IZone[] = [...additionalZones, ...zoneCollection];
        spyOn(zoneService, 'addZoneToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ point });
        comp.ngOnInit();

        expect(zoneService.query).toHaveBeenCalled();
        expect(zoneService.addZoneToCollectionIfMissing).toHaveBeenCalledWith(zoneCollection, ...additionalZones);
        expect(comp.zonesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const point: IPoint = { id: 456 };
        const zone: IZone = { id: 95416 };
        point.zone = zone;

        activatedRoute.data = of({ point });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(point));
        expect(comp.zonesSharedCollection).toContain(zone);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const point = { id: 123 };
        spyOn(pointService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
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
        const saveSubject = new Subject();
        const point = new Point();
        spyOn(pointService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
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
        const saveSubject = new Subject();
        const point = { id: 123 };
        spyOn(pointService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
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
      describe('trackZoneById', () => {
        it('Should return tracked Zone primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackZoneById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
