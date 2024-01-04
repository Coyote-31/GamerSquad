import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IPlayerFriendship } from '../../../models/player-friendship.model';

@Component({
  selector: 'app-friends-delete-dialog',
  templateUrl: './friends-delete-dialog.component.html',
  styleUrls: ['./friends-delete-dialog.component.scss'],
})
export class FriendsDeleteDialogComponent {
  friend!: IPlayerFriendship;

  constructor(protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(appUserId: number): void {
    this.activeModal.close(appUserId);
  }
}
