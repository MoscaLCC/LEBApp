jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TransporterService } from '../service/transporter.service';
import { ITransporter, Transporter } from '../transporter.model';
import { IRoute } from 'app/entities/route/route.model';
import { RouteService } from 'app/entities/route/service/route.service';

import { TransporterUpdateComponent } from './transporter-update.component';

describe('Component Tests', () => {
  describe('Transporter Management Update Component', () => {
    let comp: TransporterUpdateComponent;
    let fixture: ComponentFixture<TransporterUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let transporterService: TransporterService;
    let routeService: RouteService;

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
      routeService = TestBed.inject(RouteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Route query and add missing value', () => {
        const transporter: ITransporter = { id: 456 };
        const routes: IRoute[] = [{ id: 81961 }];
        transporter.routes = routes;

        const routeCollection: IRoute[] = [{ id: 97737 }];
        spyOn(routeService, 'query').and.returnValue(of(new HttpResponse({ body: routeCollection })));
        const additionalRoutes = [...routes];
        const expectedCollection: IRoute[] = [...additionalRoutes, ...routeCollection];
        spyOn(routeService, 'addRouteToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ transporter });
        comp.ngOnInit();

        expect(routeService.query).toHaveBeenCalled();
        expect(routeService.addRouteToCollectionIfMissing).toHaveBeenCalledWith(routeCollection, ...additionalRoutes);
        expect(comp.routesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const transporter: ITransporter = { id: 456 };
        const routes: IRoute = { id: 67303 };
        transporter.routes = [routes];

        activatedRoute.data = of({ transporter });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(transporter));
        expect(comp.routesSharedCollection).toContain(routes);
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
      describe('trackRouteById', () => {
        it('Should return tracked Route primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRouteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedRoute', () => {
        it('Should return option if no Route is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedRoute(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Route for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedRoute(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Route is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedRoute(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
