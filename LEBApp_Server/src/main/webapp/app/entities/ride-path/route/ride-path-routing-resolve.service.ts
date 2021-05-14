import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRidePath, RidePath } from '../ride-path.model';
import { RidePathService } from '../service/ride-path.service';

@Injectable({ providedIn: 'root' })
export class RidePathRoutingResolveService implements Resolve<IRidePath> {
  constructor(protected service: RidePathService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRidePath> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ridePath: HttpResponse<RidePath>) => {
          if (ridePath.body) {
            return of(ridePath.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RidePath());
  }
}
