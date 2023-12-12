import { Component, Input } from '@angular/core';
import { IPlayerFriendship } from '../../../models/player-friendship.model';

@Component({
  selector: 'app-appusers-list-item',
  templateUrl: './appusers-list-item.component.html',
  styleUrls: ['./appusers-list-item.component.scss'],
})
export class AppusersListItemComponent {
  @Input() player!: IPlayerFriendship;
}
