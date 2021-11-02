import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'request',
        data: { pageTitle: 'Requests' },
        loadChildren: () => import('./request/request.module').then(m => m.RequestModule),
      },
      {
        path: 'user-info',
        data: { pageTitle: 'UserInfos' },
        loadChildren: () => import('./user-info/user-info.module').then(m => m.UserInfoModule),
      },
      {
        path: 'ride-path',
        data: { pageTitle: 'RidePaths' },
        loadChildren: () => import('./ride-path/ride-path.module').then(m => m.RidePathModule),
      },
      {
        path: 'point',
        data: { pageTitle: 'Points' },
        loadChildren: () => import('./point/point.module').then(m => m.PointModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
