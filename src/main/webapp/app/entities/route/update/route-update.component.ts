import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRoute, Route } from '../route.model';
import { RouteService } from '../service/route.service';

@Component({
  selector: 'jhi-route-update',
  templateUrl: './route-update.component.html',
})
export class RouteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected routeService: RouteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ route }) => {
      this.updateForm(route);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const route = this.createFromForm();
    if (route.id !== undefined) {
      this.subscribeToSaveResponse(this.routeService.update(route));
    } else {
      this.subscribeToSaveResponse(this.routeService.create(route));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoute>>): void {
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

  protected updateForm(route: IRoute): void {
    this.editForm.patchValue({
      id: route.id,
    });
  }

  protected createFromForm(): IRoute {
    return {
      ...new Route(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
