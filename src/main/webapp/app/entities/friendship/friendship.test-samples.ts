import { IFriendship, NewFriendship } from './friendship.model';

export const sampleWithRequiredData: IFriendship = {
  id: 12823,
  isAccepted: true,
};

export const sampleWithPartialData: IFriendship = {
  id: 88722,
  isAccepted: true,
};

export const sampleWithFullData: IFriendship = {
  id: 54426,
  isAccepted: true,
};

export const sampleWithNewData: NewFriendship = {
  isAccepted: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
