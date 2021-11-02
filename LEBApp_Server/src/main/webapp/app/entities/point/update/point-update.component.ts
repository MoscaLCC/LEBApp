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

@Component({
  selector: 'jhi-point-update',
  templateUrl: './point-update.component.html',
})
export class PointUpdateComponent implements OnInit {
  isSaving = false;

  userInfosSharedCollection: IUserInfo[] = [];

  editForm = this.fb.group({
    id: [],
    openingTime: [],
    closingTime: [],
    address: [],
    numberOfDeliveries: [],
    ownerPoint: [null, Validators.required],
  });

  constructor(
    protected pointService: PointService,
    protected userInfoService: UserInfoService,
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
      closingTime: point.closingTime,
      address: point.address,
      numberOfDeliveries: point.numberOfDeliveries,
      ownerPoint: point.ownerPoint,
    });

    this.userInfosSharedCollection = this.userInfoService.addUserInfoToCollectionIfMissing(
      this.userInfosSharedCollection,
      point.ownerPoint
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userInfoService
      .query()
      .pipe(map((res: HttpResponse<IUserInfo[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfo[]) =>
          this.userInfoService.addUserInfoToCollectionIfMissing(userInfos, this.editForm.get('ownerPoint')!.value)
        )
      )
      .subscribe((userInfos: IUserInfo[]) => (this.userInfosSharedCollection = userInfos));
  }

  protected createFromForm(): IPoint {
    return {
      ...new Point(),
      id: this.editForm.get(['id'])!.value,
      openingTime: this.editForm.get(['openingTime'])!.value,
      closingTime: this.editForm.get(['closingTime'])!.value,
      address: this.editForm.get(['address'])!.value,
      numberOfDeliveries: this.editForm.get(['numberOfDeliveries'])!.value,
      ownerPoint: this.editForm.get(['ownerPoint'])!.value,
    };
  }
}
