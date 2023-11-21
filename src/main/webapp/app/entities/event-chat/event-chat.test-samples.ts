import dayjs from 'dayjs/esm';

import { IEventChat, NewEventChat } from './event-chat.model';

export const sampleWithRequiredData: IEventChat = {
  id: 6960,
  message: 'Stagiaire payment',
  sendAt: dayjs('2023-11-21T01:28'),
};

export const sampleWithPartialData: IEventChat = {
  id: 91723,
  message: 'Sausages Refined',
  sendAt: dayjs('2023-11-21T10:00'),
};

export const sampleWithFullData: IEventChat = {
  id: 25134,
  message: 'invoice',
  sendAt: dayjs('2023-11-21T00:23'),
};

export const sampleWithNewData: NewEventChat = {
  message: 'Tuna',
  sendAt: dayjs('2023-11-21T18:43'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
