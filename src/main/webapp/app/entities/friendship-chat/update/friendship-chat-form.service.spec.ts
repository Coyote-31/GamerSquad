import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../friendship-chat.test-samples';

import { FriendshipChatFormService } from './friendship-chat-form.service';

describe('FriendshipChat Form Service', () => {
  let service: FriendshipChatFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FriendshipChatFormService);
  });

  describe('Service methods', () => {
    describe('createFriendshipChatFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFriendshipChatFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            message: expect.any(Object),
            sendAt: expect.any(Object),
            friendship: expect.any(Object),
          })
        );
      });

      it('passing IFriendshipChat should create a new form with FormGroup', () => {
        const formGroup = service.createFriendshipChatFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            message: expect.any(Object),
            sendAt: expect.any(Object),
            friendship: expect.any(Object),
          })
        );
      });
    });

    describe('getFriendshipChat', () => {
      it('should return NewFriendshipChat for default FriendshipChat initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFriendshipChatFormGroup(sampleWithNewData);

        const friendshipChat = service.getFriendshipChat(formGroup) as any;

        expect(friendshipChat).toMatchObject(sampleWithNewData);
      });

      it('should return NewFriendshipChat for empty FriendshipChat initial value', () => {
        const formGroup = service.createFriendshipChatFormGroup();

        const friendshipChat = service.getFriendshipChat(formGroup) as any;

        expect(friendshipChat).toMatchObject({});
      });

      it('should return IFriendshipChat', () => {
        const formGroup = service.createFriendshipChatFormGroup(sampleWithRequiredData);

        const friendshipChat = service.getFriendshipChat(formGroup) as any;

        expect(friendshipChat).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFriendshipChat should not enable id FormControl', () => {
        const formGroup = service.createFriendshipChatFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFriendshipChat should disable id FormControl', () => {
        const formGroup = service.createFriendshipChatFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
