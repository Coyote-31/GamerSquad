import dayjs from 'dayjs/esm';
import { IFriendship } from 'app/entities/friendship/friendship.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';

export interface IFriendshipChat {
  id: number;
  message?: string | null;
  sendAt?: dayjs.Dayjs | null;
  friendship?: Pick<IFriendship, 'id'> | null;
  sender?: Pick<IAppUser, 'id'> | null;
}

export type NewFriendshipChat = Omit<IFriendshipChat, 'id'> & { id: null };
