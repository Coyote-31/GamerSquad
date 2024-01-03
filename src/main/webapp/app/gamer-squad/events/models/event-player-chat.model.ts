import dayjs from 'dayjs/esm';

export interface IEventPlayerChat {
  senderUserId: number;
  senderUserLogin: string;
  senderUserImageUrl: string;
  senderAppUserId: number;
  eventId: number;
  eventChatId: number;
  eventChatMessage: string;
  eventChatSendAt: dayjs.Dayjs;
}
