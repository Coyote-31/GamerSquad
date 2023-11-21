import { IAppUser } from 'app/entities/app-user/app-user.model';

export interface IFriendship {
  id: number;
  isAccepted?: boolean | null;
  appUserOwner?: Pick<IAppUser, 'id'> | null;
  appUserReceiver?: Pick<IAppUser, 'id'> | null;
}

export type NewFriendship = Omit<IFriendship, 'id'> & { id: null };
