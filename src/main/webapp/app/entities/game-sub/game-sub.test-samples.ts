import { IGameSub, NewGameSub } from './game-sub.model';

export const sampleWithRequiredData: IGameSub = {
  id: 99451,
};

export const sampleWithPartialData: IGameSub = {
  id: 36239,
};

export const sampleWithFullData: IGameSub = {
  id: 83273,
};

export const sampleWithNewData: NewGameSub = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
