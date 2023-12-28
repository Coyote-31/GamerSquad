import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IEventFriend } from '../../models/event-friend.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-events-invite-modal',
  templateUrl: './events-invite-modal.component.html',
  styleUrls: ['./events-invite-modal.component.scss'],
})
export class EventsInviteModalComponent {
  @Input() friends!: IEventFriend[];

  @Output() inviteEvent = new EventEmitter<number>();

  constructor(protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  onInvite(appUserId: number): void {
    this.inviteEvent.emit(appUserId);
  }
}
