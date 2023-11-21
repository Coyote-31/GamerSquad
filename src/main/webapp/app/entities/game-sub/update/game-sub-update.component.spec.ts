import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GameSubFormService } from './game-sub-form.service';
import { GameSubService } from '../service/game-sub.service';
import { IGameSub } from '../game-sub.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { IGame } from 'app/entities/game/game.model';
import { GameService } from 'app/entities/game/service/game.service';

import { GameSubUpdateComponent } from './game-sub-update.component';

describe('GameSub Management Update Component', () => {
  let comp: GameSubUpdateComponent;
  let fixture: ComponentFixture<GameSubUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let gameSubFormService: GameSubFormService;
  let gameSubService: GameSubService;
  let appUserService: AppUserService;
  let gameService: GameService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GameSubUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(GameSubUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GameSubUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    gameSubFormService = TestBed.inject(GameSubFormService);
    gameSubService = TestBed.inject(GameSubService);
    appUserService = TestBed.inject(AppUserService);
    gameService = TestBed.inject(GameService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AppUser query and add missing value', () => {
      const gameSub: IGameSub = { id: 456 };
      const appUser: IAppUser = { id: 41542 };
      gameSub.appUser = appUser;

      const appUserCollection: IAppUser[] = [{ id: 27225 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [appUser];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ gameSub });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining)
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Game query and add missing value', () => {
      const gameSub: IGameSub = { id: 456 };
      const game: IGame = { id: 63836 };
      gameSub.game = game;

      const gameCollection: IGame[] = [{ id: 49002 }];
      jest.spyOn(gameService, 'query').mockReturnValue(of(new HttpResponse({ body: gameCollection })));
      const additionalGames = [game];
      const expectedCollection: IGame[] = [...additionalGames, ...gameCollection];
      jest.spyOn(gameService, 'addGameToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ gameSub });
      comp.ngOnInit();

      expect(gameService.query).toHaveBeenCalled();
      expect(gameService.addGameToCollectionIfMissing).toHaveBeenCalledWith(
        gameCollection,
        ...additionalGames.map(expect.objectContaining)
      );
      expect(comp.gamesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const gameSub: IGameSub = { id: 456 };
      const appUser: IAppUser = { id: 96667 };
      gameSub.appUser = appUser;
      const game: IGame = { id: 19283 };
      gameSub.game = game;

      activatedRoute.data = of({ gameSub });
      comp.ngOnInit();

      expect(comp.appUsersSharedCollection).toContain(appUser);
      expect(comp.gamesSharedCollection).toContain(game);
      expect(comp.gameSub).toEqual(gameSub);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGameSub>>();
      const gameSub = { id: 123 };
      jest.spyOn(gameSubFormService, 'getGameSub').mockReturnValue(gameSub);
      jest.spyOn(gameSubService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gameSub });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gameSub }));
      saveSubject.complete();

      // THEN
      expect(gameSubFormService.getGameSub).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(gameSubService.update).toHaveBeenCalledWith(expect.objectContaining(gameSub));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGameSub>>();
      const gameSub = { id: 123 };
      jest.spyOn(gameSubFormService, 'getGameSub').mockReturnValue({ id: null });
      jest.spyOn(gameSubService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gameSub: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gameSub }));
      saveSubject.complete();

      // THEN
      expect(gameSubFormService.getGameSub).toHaveBeenCalled();
      expect(gameSubService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGameSub>>();
      const gameSub = { id: 123 };
      jest.spyOn(gameSubService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gameSub });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(gameSubService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAppUser', () => {
      it('Should forward to appUserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(appUserService, 'compareAppUser');
        comp.compareAppUser(entity, entity2);
        expect(appUserService.compareAppUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGame', () => {
      it('Should forward to gameService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(gameService, 'compareGame');
        comp.compareGame(entity, entity2);
        expect(gameService.compareGame).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
