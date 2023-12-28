import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';

@Component({
  selector: 'app-my-events-pending-list-item',
  templateUrl: './my-events-pending-list-item.component.html',
  styleUrls: ['./my-events-pending-list-item.component.scss'],
})
export class MyEventsPendingListItemComponent {
  @Input() event!: IEventDetail;

  @Output() acceptInviteEvent = new EventEmitter<number>();
  @Output() refuseInviteEvent = new EventEmitter<number>();

  onAcceptInvite(): void {
    this.acceptInviteEvent.emit(this.event.id);
  }

  onRefuseInvite(): void {
    this.refuseInviteEvent.emit(this.event.id);
  }
}
