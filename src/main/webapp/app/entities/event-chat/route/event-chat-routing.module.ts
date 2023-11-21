import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventChatComponent } from '../list/event-chat.component';
import { EventChatDetailComponent } from '../detail/event-chat-detail.component';
import { EventChatUpdateComponent } from '../update/event-chat-update.component';
import { EventChatRoutingResolveService } from './event-chat-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventChatRoute: Routes = [
  {
    path: '',
    component: EventChatComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventChatDetailComponent,
    resolve: {
      eventChat: EventChatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventChatUpdateComponent,
    resolve: {
      eventChat: EventChatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventChatUpdateComponent,
    resolve: {
      eventChat: EventChatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventChatRoute)],
  exports: [RouterModule],
})
export class EventChatRoutingModule {}
