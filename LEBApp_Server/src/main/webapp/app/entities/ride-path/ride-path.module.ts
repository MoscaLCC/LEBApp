import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { RidePathComponent } from './list/ride-path.component';
import { RidePathDetailComponent } from './detail/ride-path-detail.component';
import { RidePathUpdateComponent } from './update/ride-path-update.component';
import { RidePathDeleteDialogComponent } from './delete/ride-path-delete-dialog.component';
import { RidePathRoutingModule } from './route/ride-path-routing.module';

@NgModule({
  imports: [SharedModule, RidePathRoutingModule],
  declarations: [RidePathComponent, RidePathDetailComponent, RidePathUpdateComponent, RidePathDeleteDialogComponent],
  entryComponents: [RidePathDeleteDialogComponent],
})
export class RidePathModule {}
