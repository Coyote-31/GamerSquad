import { Component, Input, OnInit } from '@angular/core';
import { IGame } from '../../../../entities/game/game.model';

@Component({
  selector: 'app-games-list-item',
  templateUrl: './games-list-item.component.html',
  styleUrls: ['./games-list-item.component.scss'],
})
export class GamesListItemComponent implements OnInit {
  @Input() game!: IGame;

  constructor() {}

  ngOnInit(): void {}
}
