import { IGame, NewGame } from './game.model';

export const sampleWithRequiredData: IGame = {
  id: 79326,
  title: 'Metal withdrawal',
};

export const sampleWithPartialData: IGame = {
  id: 53329,
  title: 'online reintermediate',
  imgUrl: 'Shirt Loan',
};

export const sampleWithFullData: IGame = {
  id: 77909,
  title: 'Handmade',
  description: 'Account b',
  imgUrl: 'Keyboard gold',
};

export const sampleWithNewData: NewGame = {
  title: 'Executif Ingenieur Royale',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
