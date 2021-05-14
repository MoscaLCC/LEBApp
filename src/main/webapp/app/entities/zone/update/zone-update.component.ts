import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IZone, Zone } from '../zone.model';
import { ZoneService } from '../service/zone.service';
import { ITransporter } from 'app/entities/transporter/transporter.model';
import { TransporterService } from 'app/entities/transporter/service/transporter.service';

@Component({
  selector: 'jhi-zone-update',
  templateUrl: './zone-update.component.html',
})
export class ZoneUpdateComponent implements OnInit {
  isSaving = false;

  transportersSharedCollection: ITransporter[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    transporters: [],
  });

  constructor(
    protected zoneService: ZoneService,
    protected transporterService: TransporterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zone }) => {
      this.updateForm(zone);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const zone = this.createFromForm();
    if (zone.id !== undefined) {
      this.subscribeToSaveResponse(this.zoneService.update(zone));
    } else {
      this.subscribeToSaveResponse(this.zoneService.create(zone));
    }
  }

  trackTransporterById(index: number, item: ITransporter): number {
    return item.id!;
  }

  getSelectedTransporter(option: ITransporter, selectedVals?: ITransporter[]): ITransporter {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZone>>): void {
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

  protected updateForm(zone: IZone): void {
    this.editForm.patchValue({
      id: zone.id,
      name: zone.name,
      transporters: zone.transporters,
    });

    this.transportersSharedCollection = this.transporterService.addTransporterToCollectionIfMissing(
      this.transportersSharedCollection,
      ...(zone.transporters ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.transporterService
      .query()
      .pipe(map((res: HttpResponse<ITransporter[]>) => res.body ?? []))
      .pipe(
        map((transporters: ITransporter[]) =>
          this.transporterService.addTransporterToCollectionIfMissing(transporters, ...(this.editForm.get('transporters')!.value ?? []))
        )
      )
      .subscribe((transporters: ITransporter[]) => (this.transportersSharedCollection = transporters));
  }

  protected createFromForm(): IZone {
    return {
      ...new Zone(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      transporters: this.editForm.get(['transporters'])!.value,
    };
  }
}
