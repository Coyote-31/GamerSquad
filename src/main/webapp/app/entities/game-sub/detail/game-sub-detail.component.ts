import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGameSub } from '../game-sub.model';

@Component({
  selector: 'jhi-game-sub-detail',
  templateUrl: './game-sub-detail.component.html',
})
export class GameSubDetailComponent implements OnInit {
  gameSub: IGameSub | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gameSub }) => {
      this.gameSub = gameSub;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
