import { Component, OnInit } from '@angular/core';
import { IGame } from '../../../../entities/game/game.model';
import { Observable } from 'rxjs';
import { GamesService } from '../../services/games.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-games-detail',
  templateUrl: './games-detail.component.html',
  styleUrls: ['./games-detail.component.scss'],
})
export class GamesDetailComponent implements OnInit {
  game$!: Observable<IGame>;

  constructor(private gamesService: GamesService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const gameId = +this.route.snapshot.params['id'];
    this.game$ = this.gamesService.find(gameId);
  }
}
