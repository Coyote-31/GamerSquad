import dayjs from 'dayjs/esm';

import { IFriendshipChat, NewFriendshipChat } from './friendship-chat.model';

export const sampleWithRequiredData: IFriendshipChat = {
  id: 14158,
  message: 'e-services Auto',
  sendAt: dayjs('2023-11-21T09:22'),
};

export const sampleWithPartialData: IFriendshipChat = {
  id: 62856,
  message: 'generate',
  sendAt: dayjs('2023-11-21T18:54'),
};

export const sampleWithFullData: IFriendshipChat = {
  id: 62073,
  message: 'Tasty',
  sendAt: dayjs('2023-11-21T18:43'),
};

export const sampleWithNewData: NewFriendshipChat = {
  message: 'quantifying Limousin back',
  sendAt: dayjs('2023-11-20T20:08'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
