import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEventChat, NewEventChat } from '../event-chat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventChat for edit and NewEventChatFormGroupInput for create.
 */
type EventChatFormGroupInput = IEventChat | PartialWithRequiredKeyOf<NewEventChat>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEventChat | NewEventChat> = Omit<T, 'sendAt'> & {
  sendAt?: string | null;
};

type EventChatFormRawValue = FormValueOf<IEventChat>;

type NewEventChatFormRawValue = FormValueOf<NewEventChat>;

type EventChatFormDefaults = Pick<NewEventChat, 'id' | 'sendAt'>;

type EventChatFormGroupContent = {
  id: FormControl<EventChatFormRawValue['id'] | NewEventChat['id']>;
  message: FormControl<EventChatFormRawValue['message']>;
  sendAt: FormControl<EventChatFormRawValue['sendAt']>;
  event: FormControl<EventChatFormRawValue['event']>;
  appUser: FormControl<EventChatFormRawValue['appUser']>;
};

export type EventChatFormGroup = FormGroup<EventChatFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventChatFormService {
  createEventChatFormGroup(eventChat: EventChatFormGroupInput = { id: null }): EventChatFormGroup {
    const eventChatRawValue = this.convertEventChatToEventChatRawValue({
      ...this.getFormDefaults(),
      ...eventChat,
    });
    return new FormGroup<EventChatFormGroupContent>({
      id: new FormControl(
        { value: eventChatRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      message: new FormControl(eventChatRawValue.message, {
        validators: [Validators.required, Validators.maxLength(512)],
      }),
      sendAt: new FormControl(eventChatRawValue.sendAt, {
        validators: [Validators.required],
      }),
      event: new FormControl(eventChatRawValue.event, {
        validators: [Validators.required],
      }),
      appUser: new FormControl(eventChatRawValue.appUser, {
        validators: [Validators.required],
      }),
    });
  }

  getEventChat(form: EventChatFormGroup): IEventChat | NewEventChat {
    return this.convertEventChatRawValueToEventChat(form.getRawValue() as EventChatFormRawValue | NewEventChatFormRawValue);
  }

  resetForm(form: EventChatFormGroup, eventChat: EventChatFormGroupInput): void {
    const eventChatRawValue = this.convertEventChatToEventChatRawValue({ ...this.getFormDefaults(), ...eventChat });
    form.reset(
      {
        ...eventChatRawValue,
        id: { value: eventChatRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventChatFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      sendAt: currentTime,
    };
  }

  private convertEventChatRawValueToEventChat(rawEventChat: EventChatFormRawValue | NewEventChatFormRawValue): IEventChat | NewEventChat {
    return {
      ...rawEventChat,
      sendAt: dayjs(rawEventChat.sendAt, DATE_TIME_FORMAT),
    };
  }

  private convertEventChatToEventChatRawValue(
    eventChat: IEventChat | (Partial<NewEventChat> & EventChatFormDefaults)
  ): EventChatFormRawValue | PartialWithRequiredKeyOf<NewEventChatFormRawValue> {
    return {
      ...eventChat,
      sendAt: eventChat.sendAt ? eventChat.sendAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
