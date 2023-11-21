import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventSub } from '../event-sub.model';
import { EventSubService } from '../service/event-sub.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-sub-delete-dialog.component.html',
})
export class EventSubDeleteDialogComponent {
  eventSub?: IEventSub;

  constructor(protected eventSubService: EventSubService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventSubService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
