import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DimensionsComponent } from '../list/dimensions.component';
import { DimensionsDetailComponent } from '../detail/dimensions-detail.component';
import { DimensionsUpdateComponent } from '../update/dimensions-update.component';
import { DimensionsRoutingResolveService } from './dimensions-routing-resolve.service';

const dimensionsRoute: Routes = [
  {
    path: '',
    component: DimensionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DimensionsDetailComponent,
    resolve: {
      dimensions: DimensionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DimensionsUpdateComponent,
    resolve: {
      dimensions: DimensionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DimensionsUpdateComponent,
    resolve: {
      dimensions: DimensionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dimensionsRoute)],
  exports: [RouterModule],
})
export class DimensionsRoutingModule {}
