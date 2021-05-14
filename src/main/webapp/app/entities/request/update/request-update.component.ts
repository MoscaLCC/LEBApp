import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRequest, Request } from '../request.model';
import { RequestService } from '../service/request.service';
import { IDimensions } from 'app/entities/dimensions/dimensions.model';
import { DimensionsService } from 'app/entities/dimensions/service/dimensions.service';
import { IRoute } from 'app/entities/route/route.model';
import { RouteService } from 'app/entities/route/service/route.service';
import { IProducer } from 'app/entities/producer/producer.model';
import { ProducerService } from 'app/entities/producer/service/producer.service';

@Component({
  selector: 'jhi-request-update',
  templateUrl: './request-update.component.html',
})
export class RequestUpdateComponent implements OnInit {
  isSaving = false;

  dimensionsCollection: IDimensions[] = [];
  routesSharedCollection: IRoute[] = [];
  producersSharedCollection: IProducer[] = [];

  editForm = this.fb.group({
    id: [],
    productValue: [],
    productName: [],
    source: [],
    destination: [],
    destinationContact: [],
    initDate: [],
    expirationDate: [],
    description: [],
    specialCharacteristics: [],
    productWeight: [],
    status: [],
    estimatedDate: [],
    deliveryTime: [],
    shippingCosts: [],
    rating: [],
    dimensions: [],
    route: [null, Validators.required],
    producer: [null, Validators.required],
  });

  constructor(
    protected requestService: RequestService,
    protected dimensionsService: DimensionsService,
    protected routeService: RouteService,
    protected producerService: ProducerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ request }) => {
      if (request.id === undefined) {
        const today = dayjs().startOf('day');
        request.initDate = today;
        request.expirationDate = today;
        request.estimatedDate = today;
        request.deliveryTime = today;
      }

      this.updateForm(request);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const request = this.createFromForm();
    if (request.id !== undefined) {
      this.subscribeToSaveResponse(this.requestService.update(request));
    } else {
      this.subscribeToSaveResponse(this.requestService.create(request));
    }
  }

  trackDimensionsById(index: number, item: IDimensions): number {
    return item.id!;
  }

  trackRouteById(index: number, item: IRoute): number {
    return item.id!;
  }

  trackProducerById(index: number, item: IProducer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequest>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(request: IRequest): void {
    this.editForm.patchValue({
      id: request.id,
      productValue: request.productValue,
      productName: request.productName,
      source: request.source,
      destination: request.destination,
      destinationContact: request.destinationContact,
      initDate: request.initDate ? request.initDate.format(DATE_TIME_FORMAT) : null,
      expirationDate: request.expirationDate ? request.expirationDate.format(DATE_TIME_FORMAT) : null,
      description: request.description,
      specialCharacteristics: request.specialCharacteristics,
      productWeight: request.productWeight,
      status: request.status,
      estimatedDate: request.estimatedDate ? request.estimatedDate.format(DATE_TIME_FORMAT) : null,
      deliveryTime: request.deliveryTime ? request.deliveryTime.format(DATE_TIME_FORMAT) : null,
      shippingCosts: request.shippingCosts,
      rating: request.rating,
      dimensions: request.dimensions,
      route: request.route,
      producer: request.producer,
    });

    this.dimensionsCollection = this.dimensionsService.addDimensionsToCollectionIfMissing(this.dimensionsCollection, request.dimensions);
    this.routesSharedCollection = this.routeService.addRouteToCollectionIfMissing(this.routesSharedCollection, request.route);
    this.producersSharedCollection = this.producerService.addProducerToCollectionIfMissing(
      this.producersSharedCollection,
      request.producer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dimensionsService
      .query({ 'requestId.specified': 'false' })
      .pipe(map((res: HttpResponse<IDimensions[]>) => res.body ?? []))
      .pipe(
        map((dimensions: IDimensions[]) =>
          this.dimensionsService.addDimensionsToCollectionIfMissing(dimensions, this.editForm.get('dimensions')!.value)
        )
      )
      .subscribe((dimensions: IDimensions[]) => (this.dimensionsCollection = dimensions));

    this.routeService
      .query()
      .pipe(map((res: HttpResponse<IRoute[]>) => res.body ?? []))
      .pipe(map((routes: IRoute[]) => this.routeService.addRouteToCollectionIfMissing(routes, this.editForm.get('route')!.value)))
      .subscribe((routes: IRoute[]) => (this.routesSharedCollection = routes));

    this.producerService
      .query()
      .pipe(map((res: HttpResponse<IProducer[]>) => res.body ?? []))
      .pipe(
        map((producers: IProducer[]) =>
          this.producerService.addProducerToCollectionIfMissing(producers, this.editForm.get('producer')!.value)
        )
      )
      .subscribe((producers: IProducer[]) => (this.producersSharedCollection = producers));
  }

  protected createFromForm(): IRequest {
    return {
      ...new Request(),
      id: this.editForm.get(['id'])!.value,
      productValue: this.editForm.get(['productValue'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      source: this.editForm.get(['source'])!.value,
      destination: this.editForm.get(['destination'])!.value,
      destinationContact: this.editForm.get(['destinationContact'])!.value,
      initDate: this.editForm.get(['initDate'])!.value ? dayjs(this.editForm.get(['initDate'])!.value, DATE_TIME_FORMAT) : undefined,
      expirationDate: this.editForm.get(['expirationDate'])!.value
        ? dayjs(this.editForm.get(['expirationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      description: this.editForm.get(['description'])!.value,
      specialCharacteristics: this.editForm.get(['specialCharacteristics'])!.value,
      productWeight: this.editForm.get(['productWeight'])!.value,
      status: this.editForm.get(['status'])!.value,
      estimatedDate: this.editForm.get(['estimatedDate'])!.value
        ? dayjs(this.editForm.get(['estimatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deliveryTime: this.editForm.get(['deliveryTime'])!.value
        ? dayjs(this.editForm.get(['deliveryTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      shippingCosts: this.editForm.get(['shippingCosts'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      dimensions: this.editForm.get(['dimensions'])!.value,
      route: this.editForm.get(['route'])!.value,
      producer: this.editForm.get(['producer'])!.value,
    };
  }
}
