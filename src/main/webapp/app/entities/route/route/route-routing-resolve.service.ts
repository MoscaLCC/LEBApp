import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRoute, Route } from '../route.model';
import { RouteService } from '../service/route.service';

@Injectable({ providedIn: 'root' })
export class RouteRoutingResolveService implements Resolve<IRoute> {
  constructor(protected service: RouteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoute> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((route: HttpResponse<Route>) => {
          if (route.body) {
            return of(route.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Route());
  }
}
