import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRidePath } from '../ride-path.model';
import { RidePathService } from '../service/ride-path.service';

@Component({
  templateUrl: './ride-path-delete-dialog.component.html',
})
export class RidePathDeleteDialogComponent {
  ridePath?: IRidePath;

  constructor(protected ridePathService: RidePathService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ridePathService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
