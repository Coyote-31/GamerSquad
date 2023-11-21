import dayjs from 'dayjs/esm';

import { IEvent, NewEvent } from './event.model';

export const sampleWithRequiredData: IEvent = {
  id: 63022,
  title: 'Cotton',
  meetingDate: dayjs('2023-11-21T06:45'),
  isPrivate: false,
};

export const sampleWithPartialData: IEvent = {
  id: 14349,
  title: 'Home',
  meetingDate: dayjs('2023-11-21T04:51'),
  isPrivate: false,
};

export const sampleWithFullData: IEvent = {
  id: 11383,
  title: 'one-to-one wireless',
  description: 'Outdoors Personal',
  meetingDate: dayjs('2023-11-21T02:56'),
  isPrivate: false,
};

export const sampleWithNewData: NewEvent = {
  title: 'sky',
  meetingDate: dayjs('2023-11-20T23:48'),
  isPrivate: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
