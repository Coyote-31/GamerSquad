import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IEventFriend } from '../../models/event-friend.model';

@Component({
  selector: 'app-events-invite-friends-list-item',
  templateUrl: './events-invite-friends-list-item.component.html',
  styleUrls: ['./events-invite-friends-list-item.component.scss'],
})
export class EventsInviteFriendsListItemComponent {
  @Input() friend!: IEventFriend;

  @Output() inviteEvent = new EventEmitter<number>();

  onInvite(): void {
    this.inviteEvent.emit(this.friend.appUserId);
  }
}
