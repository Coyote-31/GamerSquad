import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IGame } from '../../../../entities/game/game.model';
import { ActivatedRoute } from '@angular/router';
import { GamesService } from '../../services/games.service';
import { share } from 'rxjs/operators';

@Component({
  selector: 'app-games-list',
  templateUrl: './games-list.component.html',
  styleUrls: ['./games-list.component.scss'],
})
export class GamesListComponent implements OnInit {
  games$!: Observable<IGame[]>;

  constructor(private route: ActivatedRoute, private gameService: GamesService) {}

  ngOnInit(): void {
    this.games$ = this.gameService.getAllGames().pipe(share());
  }
}
