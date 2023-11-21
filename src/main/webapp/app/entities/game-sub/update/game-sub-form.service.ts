import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGameSub, NewGameSub } from '../game-sub.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGameSub for edit and NewGameSubFormGroupInput for create.
 */
type GameSubFormGroupInput = IGameSub | PartialWithRequiredKeyOf<NewGameSub>;

type GameSubFormDefaults = Pick<NewGameSub, 'id'>;

type GameSubFormGroupContent = {
  id: FormControl<IGameSub['id'] | NewGameSub['id']>;
  appUser: FormControl<IGameSub['appUser']>;
  game: FormControl<IGameSub['game']>;
};

export type GameSubFormGroup = FormGroup<GameSubFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GameSubFormService {
  createGameSubFormGroup(gameSub: GameSubFormGroupInput = { id: null }): GameSubFormGroup {
    const gameSubRawValue = {
      ...this.getFormDefaults(),
      ...gameSub,
    };
    return new FormGroup<GameSubFormGroupContent>({
      id: new FormControl(
        { value: gameSubRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      appUser: new FormControl(gameSubRawValue.appUser, {
        validators: [Validators.required],
      }),
      game: new FormControl(gameSubRawValue.game, {
        validators: [Validators.required],
      }),
    });
  }

  getGameSub(form: GameSubFormGroup): IGameSub | NewGameSub {
    return form.getRawValue() as IGameSub | NewGameSub;
  }

  resetForm(form: GameSubFormGroup, gameSub: GameSubFormGroupInput): void {
    const gameSubRawValue = { ...this.getFormDefaults(), ...gameSub };
    form.reset(
      {
        ...gameSubRawValue,
        id: { value: gameSubRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GameSubFormDefaults {
    return {
      id: null,
    };
  }
}
