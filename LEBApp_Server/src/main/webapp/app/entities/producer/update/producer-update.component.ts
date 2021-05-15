import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProducer, Producer } from '../producer.model';
import { ProducerService } from '../service/producer.service';
import { IUserInfo } from 'app/entities/user-info/user-info.model';
import { UserInfoService } from 'app/entities/user-info/service/user-info.service';

@Component({
  selector: 'jhi-producer-update',
  templateUrl: './producer-update.component.html',
})
export class ProducerUpdateComponent implements OnInit {
  isSaving = false;

  userInfosCollection: IUserInfo[] = [];

  editForm = this.fb.group({
    id: [],
    linkSocial: [],
    numberRequests: [],
    payedValue: [],
    valueToPay: [],
    ranking: [],
    userInfo: [null, Validators.required],
  });

  constructor(
    protected producerService: ProducerService,
    protected userInfoService: UserInfoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producer }) => {
      this.updateForm(producer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const producer = this.createFromForm();
    if (producer.id !== undefined) {
      this.subscribeToSaveResponse(this.producerService.update(producer));
    } else {
      this.subscribeToSaveResponse(this.producerService.create(producer));
    }
  }

  trackUserInfoById(index: number, item: IUserInfo): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducer>>): void {
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

  protected updateForm(producer: IProducer): void {
    this.editForm.patchValue({
      id: producer.id,
      linkSocial: producer.linkSocial,
      numberRequests: producer.numberRequests,
      payedValue: producer.payedValue,
      valueToPay: producer.valueToPay,
      ranking: producer.ranking,
      userInfo: producer.userInfo,
    });

    this.userInfosCollection = this.userInfoService.addUserInfoToCollectionIfMissing(this.userInfosCollection, producer.userInfo);
  }

  protected loadRelationshipsOptions(): void {
    this.userInfoService
      .query({ 'producerId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUserInfo[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfo[]) =>
          this.userInfoService.addUserInfoToCollectionIfMissing(userInfos, this.editForm.get('userInfo')!.value)
        )
      )
      .subscribe((userInfos: IUserInfo[]) => (this.userInfosCollection = userInfos));
  }

  protected createFromForm(): IProducer {
    return {
      ...new Producer(),
      id: this.editForm.get(['id'])!.value,
      linkSocial: this.editForm.get(['linkSocial'])!.value,
      numberRequests: this.editForm.get(['numberRequests'])!.value,
      payedValue: this.editForm.get(['payedValue'])!.value,
      valueToPay: this.editForm.get(['valueToPay'])!.value,
      ranking: this.editForm.get(['ranking'])!.value,
      userInfo: this.editForm.get(['userInfo'])!.value,
    };
  }
}
