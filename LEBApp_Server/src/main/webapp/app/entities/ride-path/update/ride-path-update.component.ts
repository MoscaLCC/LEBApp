import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRidePath, RidePath } from '../ride-path.model';
import { RidePathService } from '../service/ride-path.service';

@Component({
  selector: 'jhi-ride-path-update',
  templateUrl: './ride-path-update.component.html',
})
export class RidePathUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    source: [],
    destination: [],
    distance: [],
    estimatedTime: [],
    radius: [],
  });

  constructor(protected ridePathService: RidePathService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ridePath }) => {
      this.updateForm(ridePath);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ridePath = this.createFromForm();
    if (ridePath.id !== undefined) {
      this.subscribeToSaveResponse(this.ridePathService.update(ridePath));
    } else {
      this.subscribeToSaveResponse(this.ridePathService.create(ridePath));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRidePath>>): void {
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

  protected updateForm(ridePath: IRidePath): void {
    this.editForm.patchValue({
      id: ridePath.id,
      source: ridePath.source,
      destination: ridePath.destination,
      distance: ridePath.distance,
      estimatedTime: ridePath.estimatedTime,
      radius: ridePath.radius,
    });
  }

  protected createFromForm(): IRidePath {
    return {
      ...new RidePath(),
      id: this.editForm.get(['id'])!.value,
      source: this.editForm.get(['source'])!.value,
      destination: this.editForm.get(['destination'])!.value,
      distance: this.editForm.get(['distance'])!.value,
      estimatedTime: this.editForm.get(['estimatedTime'])!.value,
      radius: this.editForm.get(['radius'])!.value,
    };
  }
}
