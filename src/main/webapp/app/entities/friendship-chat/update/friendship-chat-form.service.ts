import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFriendshipChat, NewFriendshipChat } from '../friendship-chat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFriendshipChat for edit and NewFriendshipChatFormGroupInput for create.
 */
type FriendshipChatFormGroupInput = IFriendshipChat | PartialWithRequiredKeyOf<NewFriendshipChat>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFriendshipChat | NewFriendshipChat> = Omit<T, 'sendAt'> & {
  sendAt?: string | null;
};

type FriendshipChatFormRawValue = FormValueOf<IFriendshipChat>;

type NewFriendshipChatFormRawValue = FormValueOf<NewFriendshipChat>;

type FriendshipChatFormDefaults = Pick<NewFriendshipChat, 'id' | 'sendAt'>;

type FriendshipChatFormGroupContent = {
  id: FormControl<FriendshipChatFormRawValue['id'] | NewFriendshipChat['id']>;
  message: FormControl<FriendshipChatFormRawValue['message']>;
  sendAt: FormControl<FriendshipChatFormRawValue['sendAt']>;
  friendship: FormControl<FriendshipChatFormRawValue['friendship']>;
};

export type FriendshipChatFormGroup = FormGroup<FriendshipChatFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FriendshipChatFormService {
  createFriendshipChatFormGroup(friendshipChat: FriendshipChatFormGroupInput = { id: null }): FriendshipChatFormGroup {
    const friendshipChatRawValue = this.convertFriendshipChatToFriendshipChatRawValue({
      ...this.getFormDefaults(),
      ...friendshipChat,
    });
    return new FormGroup<FriendshipChatFormGroupContent>({
      id: new FormControl(
        { value: friendshipChatRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      message: new FormControl(friendshipChatRawValue.message, {
        validators: [Validators.required, Validators.maxLength(512)],
      }),
      sendAt: new FormControl(friendshipChatRawValue.sendAt, {
        validators: [Validators.required],
      }),
      friendship: new FormControl(friendshipChatRawValue.friendship, {
        validators: [Validators.required],
      }),
    });
  }

  getFriendshipChat(form: FriendshipChatFormGroup): IFriendshipChat | NewFriendshipChat {
    return this.convertFriendshipChatRawValueToFriendshipChat(
      form.getRawValue() as FriendshipChatFormRawValue | NewFriendshipChatFormRawValue
    );
  }

  resetForm(form: FriendshipChatFormGroup, friendshipChat: FriendshipChatFormGroupInput): void {
    const friendshipChatRawValue = this.convertFriendshipChatToFriendshipChatRawValue({ ...this.getFormDefaults(), ...friendshipChat });
    form.reset(
      {
        ...friendshipChatRawValue,
        id: { value: friendshipChatRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FriendshipChatFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      sendAt: currentTime,
    };
  }

  private convertFriendshipChatRawValueToFriendshipChat(
    rawFriendshipChat: FriendshipChatFormRawValue | NewFriendshipChatFormRawValue
  ): IFriendshipChat | NewFriendshipChat {
    return {
      ...rawFriendshipChat,
      sendAt: dayjs(rawFriendshipChat.sendAt, DATE_TIME_FORMAT),
    };
  }

  private convertFriendshipChatToFriendshipChatRawValue(
    friendshipChat: IFriendshipChat | (Partial<NewFriendshipChat> & FriendshipChatFormDefaults)
  ): FriendshipChatFormRawValue | PartialWithRequiredKeyOf<NewFriendshipChatFormRawValue> {
    return {
      ...friendshipChat,
      sendAt: friendshipChat.sendAt ? friendshipChat.sendAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
