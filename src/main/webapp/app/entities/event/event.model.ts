import dayjs from 'dayjs/esm';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { IGame } from 'app/entities/game/game.model';

export interface IEvent {
  id: number;
  title?: string | null;
  description?: string | null;
  meetingDate?: dayjs.Dayjs | null;
  isPrivate?: boolean | null;
  owner?: Pick<IAppUser, 'id'> | null;
  game?: Pick<IGame, 'id' | 'title'> | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };
