import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GameSubComponent } from '../list/game-sub.component';
import { GameSubDetailComponent } from '../detail/game-sub-detail.component';
import { GameSubUpdateComponent } from '../update/game-sub-update.component';
import { GameSubRoutingResolveService } from './game-sub-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const gameSubRoute: Routes = [
  {
    path: '',
    component: GameSubComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GameSubDetailComponent,
    resolve: {
      gameSub: GameSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GameSubUpdateComponent,
    resolve: {
      gameSub: GameSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GameSubUpdateComponent,
    resolve: {
      gameSub: GameSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(gameSubRoute)],
  exports: [RouterModule],
})
export class GameSubRoutingModule {}
