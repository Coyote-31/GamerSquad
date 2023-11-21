import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FriendshipChatComponent } from '../list/friendship-chat.component';
import { FriendshipChatDetailComponent } from '../detail/friendship-chat-detail.component';
import { FriendshipChatUpdateComponent } from '../update/friendship-chat-update.component';
import { FriendshipChatRoutingResolveService } from './friendship-chat-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const friendshipChatRoute: Routes = [
  {
    path: '',
    component: FriendshipChatComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FriendshipChatDetailComponent,
    resolve: {
      friendshipChat: FriendshipChatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FriendshipChatUpdateComponent,
    resolve: {
      friendshipChat: FriendshipChatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FriendshipChatUpdateComponent,
    resolve: {
      friendshipChat: FriendshipChatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(friendshipChatRoute)],
  exports: [RouterModule],
})
export class FriendshipChatRoutingModule {}
