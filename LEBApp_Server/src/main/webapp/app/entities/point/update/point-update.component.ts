import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPoint, Point } from '../point.model';
import { PointService } from '../service/point.service';
import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { UserInfoService } from 'app/entities/user-info/service/user-info.service';
import { IZone } from 'app/entities/zone/zone.model';
import { ZoneService } from 'app/entities/zone/service/zone.service';

@Component({
  selector: 'jhi-point-update',
  templateUrl: './point-update.component.html',
})
export class PointUpdateComponent implements OnInit {
  isSaving = false;

  userInfosCollection: IUserInfo[] = [];
  zonesSharedCollection: IZone[] = [];

  editForm = this.fb.group({
    id: [],
    openingTime: [],
    numberOfDeliveries: [],
    receivedValue: [],
    valueToReceive: [],
    ranking: [],
    userInfo: [null, Validators.required],
    zone: [],
  });

  constructor(
    protected pointService: PointService,
    protected userInfoService: UserInfoService,
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

  trackUserInfoById(index: number, item: IUserInfo): number {
    return item.id!;
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
      openingTime: point.openingTime,
      numberOfDeliveries: point.numberOfDeliveries,
      receivedValue: point.receivedValue,
      valueToReceive: point.valueToReceive,
      ranking: point.ranking,
      userInfo: point.userInfo,
      zone: point.zone,
    });

    this.userInfosCollection = this.userInfoService.addUserInfoToCollectionIfMissing(this.userInfosCollection, point.userInfo);
    this.zonesSharedCollection = this.zoneService.addZoneToCollectionIfMissing(this.zonesSharedCollection, point.zone);
  }

  protected loadRelationshipsOptions(): void {
    this.userInfoService
      .query({ 'pointId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUserInfo[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfo[]) =>
          this.userInfoService.addUserInfoToCollectionIfMissing(userInfos, this.editForm.get('userInfo')!.value)
        )
      )
      .subscribe((userInfos: IUserInfo[]) => (this.userInfosCollection = userInfos));

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
      openingTime: this.editForm.get(['openingTime'])!.value,
      numberOfDeliveries: this.editForm.get(['numberOfDeliveries'])!.value,
      receivedValue: this.editForm.get(['receivedValue'])!.value,
      valueToReceive: this.editForm.get(['valueToReceive'])!.value,
      ranking: this.editForm.get(['ranking'])!.value,
      userInfo: this.editForm.get(['userInfo'])!.value,
      zone: this.editForm.get(['zone'])!.value,
    };
  }
}
