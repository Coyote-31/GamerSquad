import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FriendshipChatComponent } from './list/friendship-chat.component';
import { FriendshipChatDetailComponent } from './detail/friendship-chat-detail.component';
import { FriendshipChatUpdateComponent } from './update/friendship-chat-update.component';
import { FriendshipChatDeleteDialogComponent } from './delete/friendship-chat-delete-dialog.component';
import { FriendshipChatRoutingModule } from './route/friendship-chat-routing.module';

@NgModule({
  imports: [SharedModule, FriendshipChatRoutingModule],
  declarations: [
    FriendshipChatComponent,
    FriendshipChatDetailComponent,
    FriendshipChatUpdateComponent,
    FriendshipChatDeleteDialogComponent,
  ],
})
export class FriendshipChatModule {}
