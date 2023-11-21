import { IEvent } from 'app/entities/event/event.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';

export interface IEventSub {
  id: number;
  isAccepted?: boolean | null;
  event?: Pick<IEvent, 'id'> | null;
  appUser?: Pick<IAppUser, 'id'> | null;
}

export type NewEventSub = Omit<IEventSub, 'id'> & { id: null };
