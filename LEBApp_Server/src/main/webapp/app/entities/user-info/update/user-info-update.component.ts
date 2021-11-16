import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserInfo, UserInfo } from '../user-info.model';
import { UserInfoService } from '../service/user-info.service';

@Component({
  selector: 'jhi-user-info-update',
  templateUrl: './user-info-update.component.html',
})
export class UserInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    phoneNumber: [],
    nib: [],
    nif: [],
    birthday: [],
    address: [],
    linkSocial: [],
    numberRequests: [],
    payedValue: [],
    valueToPay: [],
    ranking: [],
    numberOfDeliveries: [],
    numberOfKm: [],
  });

  constructor(protected userInfoService: UserInfoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userInfo }) => {
      if (userInfo.id === undefined) {
        const today = dayjs().startOf('day');
        userInfo.birthday = today;
      }

      this.updateForm(userInfo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userInfo = this.createFromForm();
    if (userInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.userInfoService.update(userInfo));
    } else {
      this.subscribeToSaveResponse(this.userInfoService.create(userInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserInfo>>): void {
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

  protected updateForm(userInfo: IUserInfo): void {
    this.editForm.patchValue({
      id: userInfo.id,
      phoneNumber: userInfo.phoneNumber,
      nib: userInfo.nib,
      nif: userInfo.nif,
      birthday: userInfo.birthday ? userInfo.birthday.format(DATE_TIME_FORMAT) : null,
      address: userInfo.address,
      linkSocial: userInfo.linkSocial,
      numberRequests: userInfo.numberRequests,
      payedValue: userInfo.payedValue,
      valueToPay: userInfo.valueToPay,
      ranking: userInfo.ranking,
      numberOfDeliveries: userInfo.numberOfDeliveries,
      numberOfKm: userInfo.numberOfKm,
    });
  }

  protected createFromForm(): IUserInfo {
    return {
      ...new UserInfo(),
      id: this.editForm.get(['id'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      nib: this.editForm.get(['nib'])!.value,
      nif: this.editForm.get(['nif'])!.value,
      birthday: this.editForm.get(['birthday'])!.value ? dayjs(this.editForm.get(['birthday'])!.value, DATE_TIME_FORMAT) : undefined,
      address: this.editForm.get(['address'])!.value,
      linkSocial: this.editForm.get(['linkSocial'])!.value,
      numberRequests: this.editForm.get(['numberRequests'])!.value,
      payedValue: this.editForm.get(['payedValue'])!.value,
      valueToPay: this.editForm.get(['valueToPay'])!.value,
      ranking: this.editForm.get(['ranking'])!.value,
      numberOfDeliveries: this.editForm.get(['numberOfDeliveries'])!.value,
      numberOfKm: this.editForm.get(['numberOfKm'])!.value,
    };
  }
}
