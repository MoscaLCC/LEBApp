jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RidePathService } from '../service/ride-path.service';
import { IRidePath, RidePath } from '../ride-path.model';

import { RidePathUpdateComponent } from './ride-path-update.component';

describe('Component Tests', () => {
  describe('RidePath Management Update Component', () => {
    let comp: RidePathUpdateComponent;
    let fixture: ComponentFixture<RidePathUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ridePathService: RidePathService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RidePathUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RidePathUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RidePathUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ridePathService = TestBed.inject(RidePathService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const ridePath: IRidePath = { id: 456 };

        activatedRoute.data = of({ ridePath });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ridePath));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ridePath = { id: 123 };
        spyOn(ridePathService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ridePath });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ridePath }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ridePathService.update).toHaveBeenCalledWith(ridePath);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ridePath = new RidePath();
        spyOn(ridePathService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ridePath });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ridePath }));
        saveSubject.complete();

        // THEN
        expect(ridePathService.create).toHaveBeenCalledWith(ridePath);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ridePath = { id: 123 };
        spyOn(ridePathService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ridePath });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ridePathService.update).toHaveBeenCalledWith(ridePath);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
