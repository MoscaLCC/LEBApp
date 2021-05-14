import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RidePathComponent } from '../list/ride-path.component';
import { RidePathDetailComponent } from '../detail/ride-path-detail.component';
import { RidePathUpdateComponent } from '../update/ride-path-update.component';
import { RidePathRoutingResolveService } from './ride-path-routing-resolve.service';

const ridePathRoute: Routes = [
  {
    path: '',
    component: RidePathComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RidePathDetailComponent,
    resolve: {
      ridePath: RidePathRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RidePathUpdateComponent,
    resolve: {
      ridePath: RidePathRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RidePathUpdateComponent,
    resolve: {
      ridePath: RidePathRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ridePathRoute)],
  exports: [RouterModule],
})
export class RidePathRoutingModule {}
