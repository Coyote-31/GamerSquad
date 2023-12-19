import dayjs from 'dayjs/esm';

export interface IPlayerChat {
  senderUserId: number;
  senderUserLogin: string;
  senderUserImageUrl: string;
  senderAppUserId: number;
  friendshipId: number;
  friendshipChatId: number;
  friendshipChatMessage: string;
  friendshipChatSendAt: dayjs.Dayjs;
}
