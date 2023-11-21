export interface IGame {
  id: number;
  title?: string | null;
  description?: string | null;
  imgUrl?: string | null;
}

export type NewGame = Omit<IGame, 'id'> & { id: null };
