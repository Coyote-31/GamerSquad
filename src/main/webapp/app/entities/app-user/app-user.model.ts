import { IUser } from 'app/entities/user/user.model';

export interface IAppUser {
  id: number;
  internalUser?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewAppUser = Omit<IAppUser, 'id'> & { id: null };
