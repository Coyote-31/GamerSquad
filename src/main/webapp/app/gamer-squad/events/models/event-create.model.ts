import dayjs from 'dayjs/esm';

export interface IEventCreate {
  title: string;
  description: string | null;
  meetingDate: dayjs.Dayjs;
  isPrivate: boolean;
}
