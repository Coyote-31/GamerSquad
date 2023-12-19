import { NgModule } from '@angular/core';
import { FriendChatsListComponent } from './components/friend-chats-list/friend-chats-list.component';
import { SharedModule } from '../../shared/shared.module';
import { FriendChatsRoutingModule } from './friend-chats-routing.module';
import { FriendChatsService } from './services/friend-chats.service';
import { FriendChatsListItemComponent } from './components/friend-chats-list-item/friend-chats-list-item.component';
import { FriendsService } from './services/friends.service';

@NgModule({
  imports: [SharedModule, FriendChatsRoutingModule],
  declarations: [FriendChatsListComponent, FriendChatsListItemComponent],
  providers: [FriendChatsService, FriendsService],
})
export class FriendChatsModule {}
