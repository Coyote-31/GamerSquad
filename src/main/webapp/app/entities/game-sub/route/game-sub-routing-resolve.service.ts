import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGameSub } from '../game-sub.model';
import { GameSubService } from '../service/game-sub.service';

@Injectable({ providedIn: 'root' })
export class GameSubRoutingResolveService implements Resolve<IGameSub | null> {
  constructor(protected service: GameSubService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGameSub | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((gameSub: HttpResponse<IGameSub>) => {
          if (gameSub.body) {
            return of(gameSub.body);
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
