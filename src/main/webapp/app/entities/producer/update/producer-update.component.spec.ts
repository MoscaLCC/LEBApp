jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProducerService } from '../service/producer.service';
import { IProducer, Producer } from '../producer.model';

import { ProducerUpdateComponent } from './producer-update.component';

describe('Component Tests', () => {
  describe('Producer Management Update Component', () => {
    let comp: ProducerUpdateComponent;
    let fixture: ComponentFixture<ProducerUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let producerService: ProducerService;

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

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const producer: IProducer = { id: 456 };

        activatedRoute.data = of({ producer });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(producer));
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
  });
});
