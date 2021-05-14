jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RouteService } from '../service/route.service';
import { IRoute, Route } from '../route.model';

import { RouteUpdateComponent } from './route-update.component';

describe('Component Tests', () => {
  describe('Route Management Update Component', () => {
    let comp: RouteUpdateComponent;
    let fixture: ComponentFixture<RouteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let routeService: RouteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RouteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RouteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RouteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      routeService = TestBed.inject(RouteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const route: IRoute = { id: 456 };

        activatedRoute.data = of({ route });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(route));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const route = { id: 123 };
        spyOn(routeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ route });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: route }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(routeService.update).toHaveBeenCalledWith(route);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const route = new Route();
        spyOn(routeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ route });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: route }));
        saveSubject.complete();

        // THEN
        expect(routeService.create).toHaveBeenCalledWith(route);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const route = { id: 123 };
        spyOn(routeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ route });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(routeService.update).toHaveBeenCalledWith(route);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
