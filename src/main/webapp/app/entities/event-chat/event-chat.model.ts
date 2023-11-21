import dayjs from 'dayjs/esm';
import { IEvent } from 'app/entities/event/event.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';

export interface IEventChat {
  id: number;
  message?: string | null;
  sendAt?: dayjs.Dayjs | null;
  event?: Pick<IEvent, 'id'> | null;
  appUser?: Pick<IAppUser, 'id'> | null;
}

export type NewEventChat = Omit<IEventChat, 'id'> & { id: null };
