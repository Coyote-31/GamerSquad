import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          path: 'games',
          data: {
            authorities: [Authority.ADMIN, Authority.USER],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import(`./gamer-squad/games/games.module`).then(m => m.GamesModule),
        },
        {
          path: 'friends',
          data: {
            authorities: [Authority.ADMIN, Authority.USER],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import(`./gamer-squad/friends/friends.module`).then(m => m.FriendsModule),
        },
        {
          path: 'friend-chats',
          data: {
            authorities: [Authority.ADMIN, Authority.USER],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import(`./gamer-squad/friend-chats/friend-chats.module`).then(m => m.FriendChatsModule),
        },
        {
          path: 'events',
          data: {
            authorities: [Authority.ADMIN, Authority.USER],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import(`./gamer-squad/events/events.module`).then(m => m.EventsModule),
        },
        {
          path: '',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import(`./entities/entity-routing.module`).then(m => m.EntityRoutingModule),
        },
        navbarRoute,
        ...errorRoute,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
