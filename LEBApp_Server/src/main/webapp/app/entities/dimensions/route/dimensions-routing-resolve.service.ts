import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDimensions, Dimensions } from '../dimensions.model';
import { DimensionsService } from '../service/dimensions.service';

@Injectable({ providedIn: 'root' })
export class DimensionsRoutingResolveService implements Resolve<IDimensions> {
  constructor(protected service: DimensionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDimensions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dimensions: HttpResponse<Dimensions>) => {
          if (dimensions.body) {
            return of(dimensions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dimensions());
  }
}
