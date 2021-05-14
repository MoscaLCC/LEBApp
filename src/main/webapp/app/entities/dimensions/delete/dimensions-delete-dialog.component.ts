import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDimensions } from '../dimensions.model';
import { DimensionsService } from '../service/dimensions.service';

@Component({
  templateUrl: './dimensions-delete-dialog.component.html',
})
export class DimensionsDeleteDialogComponent {
  dimensions?: IDimensions;

  constructor(protected dimensionsService: DimensionsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dimensionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
