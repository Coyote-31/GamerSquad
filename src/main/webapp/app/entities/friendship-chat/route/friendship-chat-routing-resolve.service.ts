import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFriendshipChat } from '../friendship-chat.model';
import { FriendshipChatService } from '../service/friendship-chat.service';

@Injectable({ providedIn: 'root' })
export class FriendshipChatRoutingResolveService implements Resolve<IFriendshipChat | null> {
  constructor(protected service: FriendshipChatService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFriendshipChat | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((friendshipChat: HttpResponse<IFriendshipChat>) => {
          if (friendshipChat.body) {
            return of(friendshipChat.body);
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
