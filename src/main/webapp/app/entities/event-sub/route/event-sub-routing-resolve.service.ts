import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventSub } from '../event-sub.model';
import { EventSubService } from '../service/event-sub.service';

@Injectable({ providedIn: 'root' })
export class EventSubRoutingResolveService implements Resolve<IEventSub | null> {
  constructor(protected service: EventSubService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventSub | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventSub: HttpResponse<IEventSub>) => {
          if (eventSub.body) {
            return of(eventSub.body);
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
