import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IGame } from '../../../../entities/game/game.model';
import { ActivatedRoute } from '@angular/router';
import { GamesService } from '../../services/games.service';
import { map, tap } from 'rxjs/operators';

@Component({
  selector: 'app-games-list',
  templateUrl: './games-list.component.html',
  styleUrls: ['./games-list.component.scss'],
})
export class GamesListComponent implements OnInit {
  games$!: Observable<IGame[]>;
  isEmpty = false;

  constructor(private route: ActivatedRoute, private gameService: GamesService) {}

  ngOnInit(): void {
    this.games$ = this.gameService.getAllGames().pipe(
      tap(games => (this.isEmpty = !games.length)),
      map(games =>
        games.sort((a, b) => {
          if (a.title === undefined) {
            return 1;
          }
          if (b.title === undefined) {
            return -1;
          }
          if (a.title === b.title) {
            return 0;
          }
          return a.title! > b.title! ? 1 : -1;
        })
      )
    );
  }
}
