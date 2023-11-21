import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFriendshipChat } from '../friendship-chat.model';
import { FriendshipChatService } from '../service/friendship-chat.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './friendship-chat-delete-dialog.component.html',
})
export class FriendshipChatDeleteDialogComponent {
  friendshipChat?: IFriendshipChat;

  constructor(protected friendshipChatService: FriendshipChatService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.friendshipChatService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
