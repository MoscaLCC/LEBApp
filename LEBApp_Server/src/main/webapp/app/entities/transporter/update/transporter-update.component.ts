import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITransporter, Transporter } from '../transporter.model';
import { TransporterService } from '../service/transporter.service';
import { IRidePath } from 'app/entities/ride-path/ride-path.model';
import { RidePathService } from 'app/entities/ride-path/service/ride-path.service';

@Component({
  selector: 'jhi-transporter-update',
  templateUrl: './transporter-update.component.html',
})
export class TransporterUpdateComponent implements OnInit {
  isSaving = false;

  ridePathsSharedCollection: IRidePath[] = [];

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
    ridePaths: [],
  });

  constructor(
    protected transporterService: TransporterService,
    protected ridePathService: RidePathService,
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

  trackRidePathById(index: number, item: IRidePath): number {
    return item.id!;
  }

  getSelectedRidePath(option: IRidePath, selectedVals?: IRidePath[]): IRidePath {
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
      ridePaths: transporter.ridePaths,
    });

    this.ridePathsSharedCollection = this.ridePathService.addRidePathToCollectionIfMissing(
      this.ridePathsSharedCollection,
      ...(transporter.ridePaths ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ridePathService
      .query()
      .pipe(map((res: HttpResponse<IRidePath[]>) => res.body ?? []))
      .pipe(
        map((ridePaths: IRidePath[]) =>
          this.ridePathService.addRidePathToCollectionIfMissing(ridePaths, ...(this.editForm.get('ridePaths')!.value ?? []))
        )
      )
      .subscribe((ridePaths: IRidePath[]) => (this.ridePathsSharedCollection = ridePaths));
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
      ridePaths: this.editForm.get(['ridePaths'])!.value,
    };
  }
}
