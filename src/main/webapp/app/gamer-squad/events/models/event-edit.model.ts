import dayjs from 'dayjs/esm';

export interface IEventEdit {
  title: string;
  description: string | null;
  meetingDate: dayjs.Dayjs;
  isPrivate: boolean;
}
