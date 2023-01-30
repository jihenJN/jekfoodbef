import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlates } from '../plates.model';
import { PlatesService } from '../service/plates.service';

@Injectable({ providedIn: 'root' })
export class PlatesRoutingResolveService implements Resolve<IPlates | null> {
  constructor(protected service: PlatesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlates | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((plates: HttpResponse<IPlates>) => {
          if (plates.body) {
            return of(plates.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
