import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventChat } from '../event-chat.model';
import { EventChatService } from '../service/event-chat.service';

@Injectable({ providedIn: 'root' })
export class EventChatRoutingResolveService implements Resolve<IEventChat | null> {
  constructor(protected service: EventChatService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventChat | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventChat: HttpResponse<IEventChat>) => {
          if (eventChat.body) {
            return of(eventChat.body);
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
