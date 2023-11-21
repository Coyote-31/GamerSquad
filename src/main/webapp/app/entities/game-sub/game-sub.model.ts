import { IAppUser } from 'app/entities/app-user/app-user.model';
import { IGame } from 'app/entities/game/game.model';

export interface IGameSub {
  id: number;
  appUser?: Pick<IAppUser, 'id'> | null;
  game?: Pick<IGame, 'id' | 'title'> | null;
}

export type NewGameSub = Omit<IGameSub, 'id'> & { id: null };
