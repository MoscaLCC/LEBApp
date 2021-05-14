jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ZoneService } from '../service/zone.service';
import { IZone, Zone } from '../zone.model';
import { ITransporter } from 'app/entities/transporter/transporter.model';
import { TransporterService } from 'app/entities/transporter/service/transporter.service';

import { ZoneUpdateComponent } from './zone-update.component';

describe('Component Tests', () => {
  describe('Zone Management Update Component', () => {
    let comp: ZoneUpdateComponent;
    let fixture: ComponentFixture<ZoneUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let zoneService: ZoneService;
    let transporterService: TransporterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ZoneUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ZoneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ZoneUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      zoneService = TestBed.inject(ZoneService);
      transporterService = TestBed.inject(TransporterService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Transporter query and add missing value', () => {
        const zone: IZone = { id: 456 };
        const transporters: ITransporter[] = [{ id: 53657 }];
        zone.transporters = transporters;

        const transporterCollection: ITransporter[] = [{ id: 54820 }];
        spyOn(transporterService, 'query').and.returnValue(of(new HttpResponse({ body: transporterCollection })));
        const additionalTransporters = [...transporters];
        const expectedCollection: ITransporter[] = [...additionalTransporters, ...transporterCollection];
        spyOn(transporterService, 'addTransporterToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ zone });
        comp.ngOnInit();

        expect(transporterService.query).toHaveBeenCalled();
        expect(transporterService.addTransporterToCollectionIfMissing).toHaveBeenCalledWith(
          transporterCollection,
          ...additionalTransporters
        );
        expect(comp.transportersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const zone: IZone = { id: 456 };
        const transporters: ITransporter = { id: 16569 };
        zone.transporters = [transporters];

        activatedRoute.data = of({ zone });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(zone));
        expect(comp.transportersSharedCollection).toContain(transporters);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const zone = { id: 123 };
        spyOn(zoneService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ zone });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: zone }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(zoneService.update).toHaveBeenCalledWith(zone);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const zone = new Zone();
        spyOn(zoneService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ zone });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: zone }));
        saveSubject.complete();

        // THEN
        expect(zoneService.create).toHaveBeenCalledWith(zone);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const zone = { id: 123 };
        spyOn(zoneService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ zone });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(zoneService.update).toHaveBeenCalledWith(zone);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTransporterById', () => {
        it('Should return tracked Transporter primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTransporterById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedTransporter', () => {
        it('Should return option if no Transporter is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedTransporter(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Transporter for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedTransporter(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Transporter is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedTransporter(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
