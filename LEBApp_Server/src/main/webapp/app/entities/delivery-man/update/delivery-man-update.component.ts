import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDeliveryMan, DeliveryMan } from '../delivery-man.model';
import { DeliveryManService } from '../service/delivery-man.service';
import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { UserInfoService } from 'app/entities/user-info/service/user-info.service';
import { IPoint } from 'app/entities/point/point.model';
import { PointService } from 'app/entities/point/service/point.service';

@Component({
  selector: 'jhi-delivery-man-update',
  templateUrl: './delivery-man-update.component.html',
})
export class DeliveryManUpdateComponent implements OnInit {
  isSaving = false;

  userInfosCollection: IUserInfo[] = [];
  pointsSharedCollection: IPoint[] = [];

  editForm = this.fb.group({
    id: [],
    openingTime: [],
    numberOfDeliveries: [],
    numberOfKm: [],
    receivedValue: [],
    valueToReceive: [],
    ranking: [],
    userInfo: [null, Validators.required],
    point: [null, Validators.required],
  });

  constructor(
    protected deliveryManService: DeliveryManService,
    protected userInfoService: UserInfoService,
    protected pointService: PointService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deliveryMan }) => {
      this.updateForm(deliveryMan);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deliveryMan = this.createFromForm();
    if (deliveryMan.id !== undefined) {
      this.subscribeToSaveResponse(this.deliveryManService.update(deliveryMan));
    } else {
      this.subscribeToSaveResponse(this.deliveryManService.create(deliveryMan));
    }
  }

  trackUserInfoById(index: number, item: IUserInfo): number {
    return item.id!;
  }

  trackPointById(index: number, item: IPoint): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryMan>>): void {
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

  protected updateForm(deliveryMan: IDeliveryMan): void {
    this.editForm.patchValue({
      id: deliveryMan.id,
      openingTime: deliveryMan.openingTime,
      numberOfDeliveries: deliveryMan.numberOfDeliveries,
      numberOfKm: deliveryMan.numberOfKm,
      receivedValue: deliveryMan.receivedValue,
      valueToReceive: deliveryMan.valueToReceive,
      ranking: deliveryMan.ranking,
      userInfo: deliveryMan.userInfo,
      point: deliveryMan.point,
    });

    this.userInfosCollection = this.userInfoService.addUserInfoToCollectionIfMissing(this.userInfosCollection, deliveryMan.userInfo);
    this.pointsSharedCollection = this.pointService.addPointToCollectionIfMissing(this.pointsSharedCollection, deliveryMan.point);
  }

  protected loadRelationshipsOptions(): void {
    this.userInfoService
      .query({ 'deliveryManId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUserInfo[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfo[]) =>
          this.userInfoService.addUserInfoToCollectionIfMissing(userInfos, this.editForm.get('userInfo')!.value)
        )
      )
      .subscribe((userInfos: IUserInfo[]) => (this.userInfosCollection = userInfos));

    this.pointService
      .query()
      .pipe(map((res: HttpResponse<IPoint[]>) => res.body ?? []))
      .pipe(map((points: IPoint[]) => this.pointService.addPointToCollectionIfMissing(points, this.editForm.get('point')!.value)))
      .subscribe((points: IPoint[]) => (this.pointsSharedCollection = points));
  }

  protected createFromForm(): IDeliveryMan {
    return {
      ...new DeliveryMan(),
      id: this.editForm.get(['id'])!.value,
      openingTime: this.editForm.get(['openingTime'])!.value,
      numberOfDeliveries: this.editForm.get(['numberOfDeliveries'])!.value,
      numberOfKm: this.editForm.get(['numberOfKm'])!.value,
      receivedValue: this.editForm.get(['receivedValue'])!.value,
      valueToReceive: this.editForm.get(['valueToReceive'])!.value,
      ranking: this.editForm.get(['ranking'])!.value,
      userInfo: this.editForm.get(['userInfo'])!.value,
      point: this.editForm.get(['point'])!.value,
    };
  }
}
