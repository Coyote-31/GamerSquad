import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IEventFriend } from '../../models/event-friend.model';

@Component({
  selector: 'app-events-invite-friends-list',
  templateUrl: './events-invite-friends-list.component.html',
  styleUrls: ['./events-invite-friends-list.component.scss'],
})
export class EventsInviteFriendsListComponent {
  @Input() friends!: IEventFriend[];

  @Output() inviteEvent = new EventEmitter<number>();

  onInvite(appUserId: number): void {
    this.inviteEvent.emit(appUserId);
  }
}
