import dayjs from 'dayjs/esm';
import { IFriendship } from 'app/entities/friendship/friendship.model';

export interface IFriendshipChat {
  id: number;
  message?: string | null;
  sendAt?: dayjs.Dayjs | null;
  friendship?: Pick<IFriendship, 'id'> | null;
}

export type NewFriendshipChat = Omit<IFriendshipChat, 'id'> & { id: null };
