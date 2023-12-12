export interface IPlayerFriendship {
  userId: number;
  userLogin: string;
  userImageUrl?: string | null;
  appUserId: number;
  friendshipId?: number | null;
  accepted?: boolean | null;
  owned?: boolean | null;
  received?: boolean | null;
}
