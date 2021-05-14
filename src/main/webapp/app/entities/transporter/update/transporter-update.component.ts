import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITransporter, Transporter } from '../transporter.model';
import { TransporterService } from '../service/transporter.service';
import { IRoute } from 'app/entities/route/route.model';
import { RouteService } from 'app/entities/route/service/route.service';

@Component({
  selector: 'jhi-transporter-update',
  templateUrl: './transporter-update.component.html',
})
export class TransporterUpdateComponent implements OnInit {
  isSaving = false;

  routesSharedCollection: IRoute[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    phoneNumber: [],
    nib: [],
    nif: [],
    birthday: [],
    address: [],
    photo: [],
    favouriteTransport: [],
    numberOfDeliveries: [],
    numberOfKm: [],
    receivedValue: [],
    valueToReceive: [],
    ranking: [],
    routes: [],
  });

  constructor(
    protected transporterService: TransporterService,
    protected routeService: RouteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transporter }) => {
      this.updateForm(transporter);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transporter = this.createFromForm();
    if (transporter.id !== undefined) {
      this.subscribeToSaveResponse(this.transporterService.update(transporter));
    } else {
      this.subscribeToSaveResponse(this.transporterService.create(transporter));
    }
  }

  trackRouteById(index: number, item: IRoute): number {
    return item.id!;
  }

  getSelectedRoute(option: IRoute, selectedVals?: IRoute[]): IRoute {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransporter>>): void {
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

  protected updateForm(transporter: ITransporter): void {
    this.editForm.patchValue({
      id: transporter.id,
      name: transporter.name,
      email: transporter.email,
      phoneNumber: transporter.phoneNumber,
      nib: transporter.nib,
      nif: transporter.nif,
      birthday: transporter.birthday,
      address: transporter.address,
      photo: transporter.photo,
      favouriteTransport: transporter.favouriteTransport,
      numberOfDeliveries: transporter.numberOfDeliveries,
      numberOfKm: transporter.numberOfKm,
      receivedValue: transporter.receivedValue,
      valueToReceive: transporter.valueToReceive,
      ranking: transporter.ranking,
      routes: transporter.routes,
    });

    this.routesSharedCollection = this.routeService.addRouteToCollectionIfMissing(
      this.routesSharedCollection,
      ...(transporter.routes ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.routeService
      .query()
      .pipe(map((res: HttpResponse<IRoute[]>) => res.body ?? []))
      .pipe(
        map((routes: IRoute[]) => this.routeService.addRouteToCollectionIfMissing(routes, ...(this.editForm.get('routes')!.value ?? [])))
      )
      .subscribe((routes: IRoute[]) => (this.routesSharedCollection = routes));
  }

  protected createFromForm(): ITransporter {
    return {
      ...new Transporter(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      nib: this.editForm.get(['nib'])!.value,
      nif: this.editForm.get(['nif'])!.value,
      birthday: this.editForm.get(['birthday'])!.value,
      address: this.editForm.get(['address'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      favouriteTransport: this.editForm.get(['favouriteTransport'])!.value,
      numberOfDeliveries: this.editForm.get(['numberOfDeliveries'])!.value,
      numberOfKm: this.editForm.get(['numberOfKm'])!.value,
      receivedValue: this.editForm.get(['receivedValue'])!.value,
      valueToReceive: this.editForm.get(['valueToReceive'])!.value,
      ranking: this.editForm.get(['ranking'])!.value,
      routes: this.editForm.get(['routes'])!.value,
    };
  }
}
