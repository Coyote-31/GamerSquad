import { Component, Input } from '@angular/core';
import { IGame } from '../../../../entities/game/game.model';

@Component({
  selector: 'app-games-list-item',
  templateUrl: './games-list-item.component.html',
  styleUrls: ['./games-list-item.component.scss'],
})
export class GamesListItemComponent {
  @Input() game!: IGame;
}
