import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FriendChatsListComponent } from './components/friend-chats-list/friend-chats-list.component';

const routes: Routes = [
  {
    path: ':id',
    title: 'Gamer Squad - Messagerie',
    component: FriendChatsListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FriendChatsRoutingModule {}
