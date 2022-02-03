import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPoint, Point } from '../point.model';
import { PointService } from '../service/point.service';

@Component({
  selector: 'jhi-point-update',
  templateUrl: './point-update.component.html',
})
export class PointUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    openingTime: [],
    closingTime: [],
    address: [],
    numberOfDeliveries: [],
    status: [],
    ownerPoint: [],
  });

  constructor(
    protected pointService: PointService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ point }) => {
      this.updateForm(point);
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
      openingTime: point.openingTime,
      closingTime: point.closingTime,
      address: point.address,
      numberOfDeliveries: point.numberOfDeliveries,
      status: point.status,
      ownerPoint: point.ownerPoint,
    });
  }

  protected createFromForm(): IPoint {
    return {
      ...new Point(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      openingTime: this.editForm.get(['openingTime'])!.value,
      closingTime: this.editForm.get(['closingTime'])!.value,
      address: this.editForm.get(['address'])!.value,
      numberOfDeliveries: this.editForm.get(['numberOfDeliveries'])!.value,
      status: this.editForm.get(['status'])!.value,
      ownerPoint: this.editForm.get(['ownerPoint'])!.value,
    };
  }
}
