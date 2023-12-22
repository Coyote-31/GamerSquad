import dayjs from 'dayjs/esm';

export interface IEventDetail {
  id: number;
  title: string;
  description: string | null;
  meetingDate: dayjs.Dayjs;
  ownerLogin: string;
  ownerImgUrl: string;
  gameId: number;
  gameTitle: string;
  gameImgUrl: string;
  private: boolean;
}
