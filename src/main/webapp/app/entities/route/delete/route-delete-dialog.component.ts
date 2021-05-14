import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoute } from '../route.model';
import { RouteService } from '../service/route.service';

@Component({
  templateUrl: './route-delete-dialog.component.html',
})
export class RouteDeleteDialogComponent {
  route?: IRoute;

  constructor(protected routeService: RouteService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.routeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
