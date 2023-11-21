import { IEventSub, NewEventSub } from './event-sub.model';

export const sampleWithRequiredData: IEventSub = {
  id: 14805,
  isAccepted: false,
};

export const sampleWithPartialData: IEventSub = {
  id: 70151,
  isAccepted: true,
};

export const sampleWithFullData: IEventSub = {
  id: 18336,
  isAccepted: true,
};

export const sampleWithNewData: NewEventSub = {
  isAccepted: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
