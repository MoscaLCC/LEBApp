import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPoint, Point } from '../point.model';
import { PointService } from '../service/point.service';
import { IZone } from 'app/entities/zone/zone.model';
import { ZoneService } from 'app/entities/zone/service/zone.service';

@Component({
  selector: 'jhi-point-update',
  templateUrl: './point-update.component.html',
})
export class PointUpdateComponent implements OnInit {
  isSaving = false;

  zonesSharedCollection: IZone[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    phoneNumber: [],
    nib: [],
    nif: [],
    address: [],
    openingTime: [],
    numberOfDeliveries: [],
    receivedValue: [],
    valueToReceive: [],
    ranking: [],
    zone: [],
  });

  constructor(
    protected pointService: PointService,
    protected zoneService: ZoneService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ point }) => {
      this.updateForm(point);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const point = this.createFromForm();
    if (point.id !== undefined) {
      this.subscribeToSaveResponse(this.pointService.update(point));
    } else {
      this.subscribeToSaveResponse(this.pointService.create(point));
    }
  }

  trackZoneById(index: number, item: IZone): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoint>>): void {
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

  protected updateForm(point: IPoint): void {
    this.editForm.patchValue({
      id: point.id,
      name: point.name,
      email: point.email,
      phoneNumber: point.phoneNumber,
      nib: point.nib,
      nif: point.nif,
      address: point.address,
      openingTime: point.openingTime,
      numberOfDeliveries: point.numberOfDeliveries,
      receivedValue: point.receivedValue,
      valueToReceive: point.valueToReceive,
      ranking: point.ranking,
      zone: point.zone,
    });

    this.zonesSharedCollection = this.zoneService.addZoneToCollectionIfMissing(this.zonesSharedCollection, point.zone);
  }

  protected loadRelationshipsOptions(): void {
    this.zoneService
      .query()
      .pipe(map((res: HttpResponse<IZone[]>) => res.body ?? []))
      .pipe(map((zones: IZone[]) => this.zoneService.addZoneToCollectionIfMissing(zones, this.editForm.get('zone')!.value)))
      .subscribe((zones: IZone[]) => (this.zonesSharedCollection = zones));
  }

  protected createFromForm(): IPoint {
    return {
      ...new Point(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      nib: this.editForm.get(['nib'])!.value,
      nif: this.editForm.get(['nif'])!.value,
      address: this.editForm.get(['address'])!.value,
      openingTime: this.editForm.get(['openingTime'])!.value,
      numberOfDeliveries: this.editForm.get(['numberOfDeliveries'])!.value,
      receivedValue: this.editForm.get(['receivedValue'])!.value,
      valueToReceive: this.editForm.get(['valueToReceive'])!.value,
      ranking: this.editForm.get(['ranking'])!.value,
      zone: this.editForm.get(['zone'])!.value,
    };
  }
}
