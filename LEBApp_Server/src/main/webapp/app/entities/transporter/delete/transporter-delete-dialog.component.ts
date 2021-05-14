import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransporter } from '../transporter.model';
import { TransporterService } from '../service/transporter.service';

@Component({
  templateUrl: './transporter-delete-dialog.component.html',
})
export class TransporterDeleteDialogComponent {
  transporter?: ITransporter;

  constructor(protected transporterService: TransporterService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transporterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
