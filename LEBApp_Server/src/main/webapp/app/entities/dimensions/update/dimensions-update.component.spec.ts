jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DimensionsService } from '../service/dimensions.service';
import { IDimensions, Dimensions } from '../dimensions.model';

import { DimensionsUpdateComponent } from './dimensions-update.component';

describe('Component Tests', () => {
  describe('Dimensions Management Update Component', () => {
    let comp: DimensionsUpdateComponent;
    let fixture: ComponentFixture<DimensionsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dimensionsService: DimensionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DimensionsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DimensionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DimensionsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dimensionsService = TestBed.inject(DimensionsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dimensions: IDimensions = { id: 456 };

        activatedRoute.data = of({ dimensions });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dimensions));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dimensions = { id: 123 };
        spyOn(dimensionsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dimensions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dimensions }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dimensionsService.update).toHaveBeenCalledWith(dimensions);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dimensions = new Dimensions();
        spyOn(dimensionsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dimensions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dimensions }));
        saveSubject.complete();

        // THEN
        expect(dimensionsService.create).toHaveBeenCalledWith(dimensions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dimensions = { id: 123 };
        spyOn(dimensionsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dimensions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dimensionsService.update).toHaveBeenCalledWith(dimensions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
