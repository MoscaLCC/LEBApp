import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProducer, Producer } from '../producer.model';
import { ProducerService } from '../service/producer.service';

@Component({
  selector: 'jhi-producer-update',
  templateUrl: './producer-update.component.html',
})
export class ProducerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    mail: [],
    phoneNumber: [],
    nib: [],
    nif: [],
    birthday: [],
    adress: [],
    photo: [],
    linkSocial: [],
    numberRequests: [],
    payedValue: [],
    valueToPay: [],
    ranking: [],
  });

  constructor(protected producerService: ProducerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producer }) => {
      this.updateForm(producer);
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
      name: producer.name,
      mail: producer.mail,
      phoneNumber: producer.phoneNumber,
      nib: producer.nib,
      nif: producer.nif,
      birthday: producer.birthday,
      adress: producer.adress,
      photo: producer.photo,
      linkSocial: producer.linkSocial,
      numberRequests: producer.numberRequests,
      payedValue: producer.payedValue,
      valueToPay: producer.valueToPay,
      ranking: producer.ranking,
    });
  }

  protected createFromForm(): IProducer {
    return {
      ...new Producer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      nib: this.editForm.get(['nib'])!.value,
      nif: this.editForm.get(['nif'])!.value,
      birthday: this.editForm.get(['birthday'])!.value,
      adress: this.editForm.get(['adress'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      linkSocial: this.editForm.get(['linkSocial'])!.value,
      numberRequests: this.editForm.get(['numberRequests'])!.value,
      payedValue: this.editForm.get(['payedValue'])!.value,
      valueToPay: this.editForm.get(['valueToPay'])!.value,
      ranking: this.editForm.get(['ranking'])!.value,
    };
  }
}
