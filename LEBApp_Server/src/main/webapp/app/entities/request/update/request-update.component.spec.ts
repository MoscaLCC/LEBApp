jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RequestService } from '../service/request.service';
import { IRequest, Request } from '../request.model';
import { IDimensions } from 'app/entities/dimensions/dimensions.model';
import { DimensionsService } from 'app/entities/dimensions/service/dimensions.service';
import { IRidePath } from 'app/entities/ride-path/ride-path.model';
import { RidePathService } from 'app/entities/ride-path/service/ride-path.service';
import { IProducer } from 'app/entities/producer/producer.model';
import { ProducerService } from 'app/entities/producer/service/producer.service';

import { RequestUpdateComponent } from './request-update.component';

describe('Component Tests', () => {
  describe('Request Management Update Component', () => {
    let comp: RequestUpdateComponent;
    let fixture: ComponentFixture<RequestUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let requestService: RequestService;
    let dimensionsService: DimensionsService;
    let ridePathService: RidePathService;
    let producerService: ProducerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RequestUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequestUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      requestService = TestBed.inject(RequestService);
      dimensionsService = TestBed.inject(DimensionsService);
      ridePathService = TestBed.inject(RidePathService);
      producerService = TestBed.inject(ProducerService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call dimensions query and add missing value', () => {
        const request: IRequest = { id: 456 };
        const dimensions: IDimensions = { id: 62726 };
        request.dimensions = dimensions;

        const dimensionsCollection: IDimensions[] = [{ id: 28628 }];
        spyOn(dimensionsService, 'query').and.returnValue(of(new HttpResponse({ body: dimensionsCollection })));
        const expectedCollection: IDimensions[] = [dimensions, ...dimensionsCollection];
        spyOn(dimensionsService, 'addDimensionsToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ request });
        comp.ngOnInit();

        expect(dimensionsService.query).toHaveBeenCalled();
        expect(dimensionsService.addDimensionsToCollectionIfMissing).toHaveBeenCalledWith(dimensionsCollection, dimensions);
        expect(comp.dimensionsCollection).toEqual(expectedCollection);
      });

      it('Should call RidePath query and add missing value', () => {
        const request: IRequest = { id: 456 };
        const ridePath: IRidePath = { id: 76898 };
        request.ridePath = ridePath;

        const ridePathCollection: IRidePath[] = [{ id: 32810 }];
        spyOn(ridePathService, 'query').and.returnValue(of(new HttpResponse({ body: ridePathCollection })));
        const additionalRidePaths = [ridePath];
        const expectedCollection: IRidePath[] = [...additionalRidePaths, ...ridePathCollection];
        spyOn(ridePathService, 'addRidePathToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ request });
        comp.ngOnInit();

        expect(ridePathService.query).toHaveBeenCalled();
        expect(ridePathService.addRidePathToCollectionIfMissing).toHaveBeenCalledWith(ridePathCollection, ...additionalRidePaths);
        expect(comp.ridePathsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Producer query and add missing value', () => {
        const request: IRequest = { id: 456 };
        const producer: IProducer = { id: 10517 };
        request.producer = producer;

        const producerCollection: IProducer[] = [{ id: 52892 }];
        spyOn(producerService, 'query').and.returnValue(of(new HttpResponse({ body: producerCollection })));
        const additionalProducers = [producer];
        const expectedCollection: IProducer[] = [...additionalProducers, ...producerCollection];
        spyOn(producerService, 'addProducerToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ request });
        comp.ngOnInit();

        expect(producerService.query).toHaveBeenCalled();
        expect(producerService.addProducerToCollectionIfMissing).toHaveBeenCalledWith(producerCollection, ...additionalProducers);
        expect(comp.producersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const request: IRequest = { id: 456 };
        const dimensions: IDimensions = { id: 69079 };
        request.dimensions = dimensions;
        const ridePath: IRidePath = { id: 82544 };
        request.ridePath = ridePath;
        const producer: IProducer = { id: 94945 };
        request.producer = producer;

        activatedRoute.data = of({ request });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(request));
        expect(comp.dimensionsCollection).toContain(dimensions);
        expect(comp.ridePathsSharedCollection).toContain(ridePath);
        expect(comp.producersSharedCollection).toContain(producer);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const request = { id: 123 };
        spyOn(requestService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ request });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: request }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(requestService.update).toHaveBeenCalledWith(request);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const request = new Request();
        spyOn(requestService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ request });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: request }));
        saveSubject.complete();

        // THEN
        expect(requestService.create).toHaveBeenCalledWith(request);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const request = { id: 123 };
        spyOn(requestService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ request });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(requestService.update).toHaveBeenCalledWith(request);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDimensionsById', () => {
        it('Should return tracked Dimensions primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDimensionsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackRidePathById', () => {
        it('Should return tracked RidePath primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRidePathById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackProducerById', () => {
        it('Should return tracked Producer primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProducerById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
