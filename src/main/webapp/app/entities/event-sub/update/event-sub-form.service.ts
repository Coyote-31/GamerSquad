import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEventSub, NewEventSub } from '../event-sub.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventSub for edit and NewEventSubFormGroupInput for create.
 */
type EventSubFormGroupInput = IEventSub | PartialWithRequiredKeyOf<NewEventSub>;

type EventSubFormDefaults = Pick<NewEventSub, 'id' | 'isAccepted'>;

type EventSubFormGroupContent = {
  id: FormControl<IEventSub['id'] | NewEventSub['id']>;
  isAccepted: FormControl<IEventSub['isAccepted']>;
  event: FormControl<IEventSub['event']>;
  appUser: FormControl<IEventSub['appUser']>;
};

export type EventSubFormGroup = FormGroup<EventSubFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventSubFormService {
  createEventSubFormGroup(eventSub: EventSubFormGroupInput = { id: null }): EventSubFormGroup {
    const eventSubRawValue = {
      ...this.getFormDefaults(),
      ...eventSub,
    };
    return new FormGroup<EventSubFormGroupContent>({
      id: new FormControl(
        { value: eventSubRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      isAccepted: new FormControl(eventSubRawValue.isAccepted, {
        validators: [Validators.required],
      }),
      event: new FormControl(eventSubRawValue.event, {
        validators: [Validators.required],
      }),
      appUser: new FormControl(eventSubRawValue.appUser, {
        validators: [Validators.required],
      }),
    });
  }

  getEventSub(form: EventSubFormGroup): IEventSub | NewEventSub {
    return form.getRawValue() as IEventSub | NewEventSub;
  }

  resetForm(form: EventSubFormGroup, eventSub: EventSubFormGroupInput): void {
    const eventSubRawValue = { ...this.getFormDefaults(), ...eventSub };
    form.reset(
      {
        ...eventSubRawValue,
        id: { value: eventSubRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventSubFormDefaults {
    return {
      id: null,
      isAccepted: false,
    };
  }
}
