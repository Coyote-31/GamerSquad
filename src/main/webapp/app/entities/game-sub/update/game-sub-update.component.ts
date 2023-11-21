import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GameSubFormService, GameSubFormGroup } from './game-sub-form.service';
import { IGameSub } from '../game-sub.model';
import { GameSubService } from '../service/game-sub.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { IGame } from 'app/entities/game/game.model';
import { GameService } from 'app/entities/game/service/game.service';

@Component({
  selector: 'jhi-game-sub-update',
  templateUrl: './game-sub-update.component.html',
})
export class GameSubUpdateComponent implements OnInit {
  isSaving = false;
  gameSub: IGameSub | null = null;

  appUsersSharedCollection: IAppUser[] = [];
  gamesSharedCollection: IGame[] = [];

  editForm: GameSubFormGroup = this.gameSubFormService.createGameSubFormGroup();

  constructor(
    protected gameSubService: GameSubService,
    protected gameSubFormService: GameSubFormService,
    protected appUserService: AppUserService,
    protected gameService: GameService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAppUser = (o1: IAppUser | null, o2: IAppUser | null): boolean => this.appUserService.compareAppUser(o1, o2);

  compareGame = (o1: IGame | null, o2: IGame | null): boolean => this.gameService.compareGame(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gameSub }) => {
      this.gameSub = gameSub;
      if (gameSub) {
        this.updateForm(gameSub);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gameSub = this.gameSubFormService.getGameSub(this.editForm);
    if (gameSub.id !== null) {
      this.subscribeToSaveResponse(this.gameSubService.update(gameSub));
    } else {
      this.subscribeToSaveResponse(this.gameSubService.create(gameSub));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGameSub>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(gameSub: IGameSub): void {
    this.gameSub = gameSub;
    this.gameSubFormService.resetForm(this.editForm, gameSub);

    this.appUsersSharedCollection = this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(
      this.appUsersSharedCollection,
      gameSub.appUser
    );
    this.gamesSharedCollection = this.gameService.addGameToCollectionIfMissing<IGame>(this.gamesSharedCollection, gameSub.game);
  }

  protected loadRelationshipsOptions(): void {
    this.appUserService
      .query()
      .pipe(map((res: HttpResponse<IAppUser[]>) => res.body ?? []))
      .pipe(map((appUsers: IAppUser[]) => this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(appUsers, this.gameSub?.appUser)))
      .subscribe((appUsers: IAppUser[]) => (this.appUsersSharedCollection = appUsers));

    this.gameService
      .query()
      .pipe(map((res: HttpResponse<IGame[]>) => res.body ?? []))
      .pipe(map((games: IGame[]) => this.gameService.addGameToCollectionIfMissing<IGame>(games, this.gameSub?.game)))
      .subscribe((games: IGame[]) => (this.gamesSharedCollection = games));
  }
}
