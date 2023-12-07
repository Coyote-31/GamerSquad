import { Component, OnInit } from '@angular/core';
import { IGame } from '../../../../entities/game/game.model';
import { Observable, switchMap } from 'rxjs';
import { GamesService } from '../../services/games.service';
import { ActivatedRoute } from '@angular/router';
import { GameSubsService } from '../../services/game-subs.service';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-games-detail',
  templateUrl: './games-detail.component.html',
  styleUrls: ['./games-detail.component.scss'],
})
export class GamesDetailComponent implements OnInit {
  gameId!: number;
  game$!: Observable<IGame>;
  isSubscribed$!: Observable<boolean>;

  activeMenu: 'players' | 'events' = 'players';

  constructor(private gamesService: GamesService, private gameSubsService: GameSubsService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.gameId = +this.route.snapshot.params['id'];
    this.game$ = this.gamesService.find(this.gameId);
    this.isSubscribed$ = this.gameSubsService.isSubscribed(this.gameId);
  }

  subscribe() {
    this.isSubscribed$ = this.gameSubsService.subscribe(this.gameId).pipe(
      switchMap(() => this.gameSubsService.isSubscribed(this.gameId)),
      catchError(() => this.gameSubsService.isSubscribed(this.gameId))
    );
  }

  unsubscribe() {
    this.isSubscribed$ = this.gameSubsService.unsubscribe(this.gameId).pipe(
      switchMap(() => this.gameSubsService.isSubscribed(this.gameId)),
      catchError(() => this.gameSubsService.isSubscribed(this.gameId))
    );
  }

  onMenu(menu: 'players' | 'events') {
    this.activeMenu = menu;
  }
}
