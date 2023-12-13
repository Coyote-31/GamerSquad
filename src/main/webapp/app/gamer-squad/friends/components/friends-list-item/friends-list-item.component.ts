import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IPlayerFriendship } from '../../../models/player-friendship.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FriendsDeleteDialogComponent } from '../friends-delete-dialog/friends-delete-dialog.component';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-friends-list-item',
  templateUrl: './friends-list-item.component.html',
  styleUrls: ['./friends-list-item.component.scss'],
})
export class FriendsListItemComponent {
  @Input() friend!: IPlayerFriendship;

  @Output() friendshipAcceptEvent = new EventEmitter<number>();
  @Output() friendshipDeleteEvent = new EventEmitter<number>();

  constructor(protected modalService: NgbModal) {}

  onAccept(friend: IPlayerFriendship): void {
    this.friendshipAcceptEvent.emit(friend.appUserId);
  }

  onRefuse(friend: IPlayerFriendship): void {
    this.friendshipDeleteEvent.emit(friend.appUserId);
  }

  onDelete(friend: IPlayerFriendship): void {
    const modalRef = this.modalService.open(FriendsDeleteDialogComponent, { backdrop: 'static', centered: true, windowClass: 'gs-modal' });
    modalRef.componentInstance.friend = friend;
    modalRef.closed.pipe(tap(appUserId => this.friendshipDeleteEvent.emit(appUserId))).subscribe();
  }
}
