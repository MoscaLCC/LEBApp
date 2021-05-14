import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DimensionsComponent } from './list/dimensions.component';
import { DimensionsDetailComponent } from './detail/dimensions-detail.component';
import { DimensionsUpdateComponent } from './update/dimensions-update.component';
import { DimensionsDeleteDialogComponent } from './delete/dimensions-delete-dialog.component';
import { DimensionsRoutingModule } from './route/dimensions-routing.module';

@NgModule({
  imports: [SharedModule, DimensionsRoutingModule],
  declarations: [DimensionsComponent, DimensionsDetailComponent, DimensionsUpdateComponent, DimensionsDeleteDialogComponent],
  entryComponents: [DimensionsDeleteDialogComponent],
})
export class DimensionsModule {}
