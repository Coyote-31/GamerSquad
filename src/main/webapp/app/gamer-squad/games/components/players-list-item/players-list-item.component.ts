import { Component, Input } from '@angular/core';
import { IPlayerFriendship } from '../../../models/player-friendship.model';

@Component({
  selector: 'app-players-list-item',
  templateUrl: './players-list-item.component.html',
  styleUrls: ['./players-list-item.component.scss'],
})
export class PlayersListItemComponent {
  @Input() player!: IPlayerFriendship;
}
