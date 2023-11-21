import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventChat } from '../event-chat.model';
import { EventChatService } from '../service/event-chat.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-chat-delete-dialog.component.html',
})
export class EventChatDeleteDialogComponent {
  eventChat?: IEventChat;

  constructor(protected eventChatService: EventChatService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventChatService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
