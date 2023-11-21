import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'app-user',
        data: { pageTitle: 'gamerSquadApp.appUser.home.title' },
        loadChildren: () => import('./app-user/app-user.module').then(m => m.AppUserModule),
      },
      {
        path: 'game',
        data: { pageTitle: 'gamerSquadApp.game.home.title' },
        loadChildren: () => import('./game/game.module').then(m => m.GameModule),
      },
      {
        path: 'game-sub',
        data: { pageTitle: 'gamerSquadApp.gameSub.home.title' },
        loadChildren: () => import('./game-sub/game-sub.module').then(m => m.GameSubModule),
      },
      {
        path: 'friendship',
        data: { pageTitle: 'gamerSquadApp.friendship.home.title' },
        loadChildren: () => import('./friendship/friendship.module').then(m => m.FriendshipModule),
      },
      {
        path: 'friendship-chat',
        data: { pageTitle: 'gamerSquadApp.friendshipChat.home.title' },
        loadChildren: () => import('./friendship-chat/friendship-chat.module').then(m => m.FriendshipChatModule),
      },
      {
        path: 'event',
        data: { pageTitle: 'gamerSquadApp.event.home.title' },
        loadChildren: () => import('./event/event.module').then(m => m.EventModule),
      },
      {
        path: 'event-sub',
        data: { pageTitle: 'gamerSquadApp.eventSub.home.title' },
        loadChildren: () => import('./event-sub/event-sub.module').then(m => m.EventSubModule),
      },
      {
        path: 'event-chat',
        data: { pageTitle: 'gamerSquadApp.eventChat.home.title' },
        loadChildren: () => import('./event-chat/event-chat.module').then(m => m.EventChatModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
