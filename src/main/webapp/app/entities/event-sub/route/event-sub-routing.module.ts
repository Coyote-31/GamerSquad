import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventSubComponent } from '../list/event-sub.component';
import { EventSubDetailComponent } from '../detail/event-sub-detail.component';
import { EventSubUpdateComponent } from '../update/event-sub-update.component';
import { EventSubRoutingResolveService } from './event-sub-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventSubRoute: Routes = [
  {
    path: '',
    component: EventSubComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventSubDetailComponent,
    resolve: {
      eventSub: EventSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventSubUpdateComponent,
    resolve: {
      eventSub: EventSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventSubUpdateComponent,
    resolve: {
      eventSub: EventSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventSubRoute)],
  exports: [RouterModule],
})
export class EventSubRoutingModule {}
