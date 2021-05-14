jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TransporterService } from '../service/transporter.service';
import { ITransporter, Transporter } from '../transporter.model';
import { IRidePath } from 'app/entities/ride-path/ride-path.model';
import { RidePathService } from 'app/entities/ride-path/service/ride-path.service';

import { TransporterUpdateComponent } from './transporter-update.component';

describe('Component Tests', () => {
  describe('Transporter Management Update Component', () => {
    let comp: TransporterUpdateComponent;
    let fixture: ComponentFixture<TransporterUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let transporterService: TransporterService;
    let ridePathService: RidePathService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TransporterUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TransporterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransporterUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      transporterService = TestBed.inject(TransporterService);
      ridePathService = TestBed.inject(RidePathService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call RidePath query and add missing value', () => {
        const transporter: ITransporter = { id: 456 };
        const ridePaths: IRidePath[] = [{ id: 72935 }];
        transporter.ridePaths = ridePaths;

        const ridePathCollection: IRidePath[] = [{ id: 5835 }];
        spyOn(ridePathService, 'query').and.returnValue(of(new HttpResponse({ body: ridePathCollection })));
        const additionalRidePaths = [...ridePaths];
        const expectedCollection: IRidePath[] = [...additionalRidePaths, ...ridePathCollection];
        spyOn(ridePathService, 'addRidePathToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ transporter });
        comp.ngOnInit();

        expect(ridePathService.query).toHaveBeenCalled();
        expect(ridePathService.addRidePathToCollectionIfMissing).toHaveBeenCalledWith(ridePathCollection, ...additionalRidePaths);
        expect(comp.ridePathsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const transporter: ITransporter = { id: 456 };
        const ridePaths: IRidePath = { id: 13491 };
        transporter.ridePaths = [ridePaths];

        activatedRoute.data = of({ transporter });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(transporter));
        expect(comp.ridePathsSharedCollection).toContain(ridePaths);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const transporter = { id: 123 };
        spyOn(transporterService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ transporter });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: transporter }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(transporterService.update).toHaveBeenCalledWith(transporter);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const transporter = new Transporter();
        spyOn(transporterService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ transporter });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: transporter }));
        saveSubject.complete();

        // THEN
        expect(transporterService.create).toHaveBeenCalledWith(transporter);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const transporter = { id: 123 };
        spyOn(transporterService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ transporter });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(transporterService.update).toHaveBeenCalledWith(transporter);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackRidePathById', () => {
        it('Should return tracked RidePath primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRidePathById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedRidePath', () => {
        it('Should return option if no RidePath is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedRidePath(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected RidePath for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedRidePath(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this RidePath is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedRidePath(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
