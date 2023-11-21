import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-chat.test-samples';

import { EventChatFormService } from './event-chat-form.service';

describe('EventChat Form Service', () => {
  let service: EventChatFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventChatFormService);
  });

  describe('Service methods', () => {
    describe('createEventChatFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventChatFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            message: expect.any(Object),
            sendAt: expect.any(Object),
            event: expect.any(Object),
            appUser: expect.any(Object),
          })
        );
      });

      it('passing IEventChat should create a new form with FormGroup', () => {
        const formGroup = service.createEventChatFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            message: expect.any(Object),
            sendAt: expect.any(Object),
            event: expect.any(Object),
            appUser: expect.any(Object),
          })
        );
      });
    });

    describe('getEventChat', () => {
      it('should return NewEventChat for default EventChat initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventChatFormGroup(sampleWithNewData);

        const eventChat = service.getEventChat(formGroup) as any;

        expect(eventChat).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventChat for empty EventChat initial value', () => {
        const formGroup = service.createEventChatFormGroup();

        const eventChat = service.getEventChat(formGroup) as any;

        expect(eventChat).toMatchObject({});
      });

      it('should return IEventChat', () => {
        const formGroup = service.createEventChatFormGroup(sampleWithRequiredData);

        const eventChat = service.getEventChat(formGroup) as any;

        expect(eventChat).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventChat should not enable id FormControl', () => {
        const formGroup = service.createEventChatFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventChat should disable id FormControl', () => {
        const formGroup = service.createEventChatFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
