import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../game-sub.test-samples';

import { GameSubFormService } from './game-sub-form.service';

describe('GameSub Form Service', () => {
  let service: GameSubFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GameSubFormService);
  });

  describe('Service methods', () => {
    describe('createGameSubFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGameSubFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            appUser: expect.any(Object),
            game: expect.any(Object),
          })
        );
      });

      it('passing IGameSub should create a new form with FormGroup', () => {
        const formGroup = service.createGameSubFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            appUser: expect.any(Object),
            game: expect.any(Object),
          })
        );
      });
    });

    describe('getGameSub', () => {
      it('should return NewGameSub for default GameSub initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGameSubFormGroup(sampleWithNewData);

        const gameSub = service.getGameSub(formGroup) as any;

        expect(gameSub).toMatchObject(sampleWithNewData);
      });

      it('should return NewGameSub for empty GameSub initial value', () => {
        const formGroup = service.createGameSubFormGroup();

        const gameSub = service.getGameSub(formGroup) as any;

        expect(gameSub).toMatchObject({});
      });

      it('should return IGameSub', () => {
        const formGroup = service.createGameSubFormGroup(sampleWithRequiredData);

        const gameSub = service.getGameSub(formGroup) as any;

        expect(gameSub).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGameSub should not enable id FormControl', () => {
        const formGroup = service.createGameSubFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGameSub should disable id FormControl', () => {
        const formGroup = service.createGameSubFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
