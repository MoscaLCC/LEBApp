import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRequest, Request } from '../request.model';
import { RequestService } from '../service/request.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-request-update',
  templateUrl: './request-update.component.html',
})
export class RequestUpdateComponent implements OnInit {
  isSaving = false;
  statusValues = Object.keys(Status);

  editForm = this.fb.group({
    id: [],
    productValue: [],
    productName: [],
    source: [],
    destination: [],
    destinationContact: [],
    initDate: [],
    expirationDate: [],
    specialCharacteristics: [],
    weight: [],
    hight: [],
    width: [],
    status: [],
    shippingCosts: [],
    rating: [],
    ownerRequest: [],
    transporter: [],
  });

  constructor(
    protected requestService: RequestService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ request }) => {
      this.updateForm(request);
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
      initDate: request.initDate,
      expirationDate: request.expirationDate,
      specialCharacteristics: request.specialCharacteristics,
      weight: request.weight,
      hight: request.hight,
      width: request.width,
      status: request.status,
      shippingCosts: request.shippingCosts,
      rating: request.rating,
      ownerRequest: request.ownerRequest,
      transporter: request.transporter,
    });
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
      initDate: this.editForm.get(['initDate'])!.value,
      expirationDate: this.editForm.get(['expirationDate'])!.value,
      specialCharacteristics: this.editForm.get(['specialCharacteristics'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      hight: this.editForm.get(['hight'])!.value,
      width: this.editForm.get(['width'])!.value,
      status: this.editForm.get(['status'])!.value,
      shippingCosts: this.editForm.get(['shippingCosts'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      ownerRequest: this.editForm.get(['ownerRequest'])!.value,
      transporter: this.editForm.get(['transporter'])!.value,
    };
  }
}
