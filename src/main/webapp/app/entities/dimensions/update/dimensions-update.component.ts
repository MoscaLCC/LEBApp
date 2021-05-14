import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDimensions, Dimensions } from '../dimensions.model';
import { DimensionsService } from '../service/dimensions.service';

@Component({
  selector: 'jhi-dimensions-update',
  templateUrl: './dimensions-update.component.html',
})
export class DimensionsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    height: [],
    width: [],
    depth: [],
  });

  constructor(protected dimensionsService: DimensionsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dimensions }) => {
      this.updateForm(dimensions);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dimensions = this.createFromForm();
    if (dimensions.id !== undefined) {
      this.subscribeToSaveResponse(this.dimensionsService.update(dimensions));
    } else {
      this.subscribeToSaveResponse(this.dimensionsService.create(dimensions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDimensions>>): void {
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

  protected updateForm(dimensions: IDimensions): void {
    this.editForm.patchValue({
      id: dimensions.id,
      height: dimensions.height,
      width: dimensions.width,
      depth: dimensions.depth,
    });
  }

  protected createFromForm(): IDimensions {
    return {
      ...new Dimensions(),
      id: this.editForm.get(['id'])!.value,
      height: this.editForm.get(['height'])!.value,
      width: this.editForm.get(['width'])!.value,
      depth: this.editForm.get(['depth'])!.value,
    };
  }
}
