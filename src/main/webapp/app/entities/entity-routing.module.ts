import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'producer',
        data: { pageTitle: 'Producers' },
        loadChildren: () => import('./producer/producer.module').then(m => m.ProducerModule),
      },
      {
        path: 'request',
        data: { pageTitle: 'Requests' },
        loadChildren: () => import('./request/request.module').then(m => m.RequestModule),
      },
      {
        path: 'route',
        data: { pageTitle: 'Routes' },
        loadChildren: () => import('./route/route.module').then(m => m.RouteModule),
      },
      {
        path: 'delivery-man',
        data: { pageTitle: 'DeliveryMen' },
        loadChildren: () => import('./delivery-man/delivery-man.module').then(m => m.DeliveryManModule),
      },
      {
        path: 'transporter',
        data: { pageTitle: 'Transporters' },
        loadChildren: () => import('./transporter/transporter.module').then(m => m.TransporterModule),
      },
      {
        path: 'point',
        data: { pageTitle: 'Points' },
        loadChildren: () => import('./point/point.module').then(m => m.PointModule),
      },
      {
        path: 'dimensions',
        data: { pageTitle: 'Dimensions' },
        loadChildren: () => import('./dimensions/dimensions.module').then(m => m.DimensionsModule),
      },
      {
        path: 'zone',
        data: { pageTitle: 'Zones' },
        loadChildren: () => import('./zone/zone.module').then(m => m.ZoneModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
