import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-sub.test-samples';

import { EventSubFormService } from './event-sub-form.service';

describe('EventSub Form Service', () => {
  let service: EventSubFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventSubFormService);
  });

  describe('Service methods', () => {
    describe('createEventSubFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventSubFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isAccepted: expect.any(Object),
            event: expect.any(Object),
            appUser: expect.any(Object),
          })
        );
      });

      it('passing IEventSub should create a new form with FormGroup', () => {
        const formGroup = service.createEventSubFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isAccepted: expect.any(Object),
            event: expect.any(Object),
            appUser: expect.any(Object),
          })
        );
      });
    });

    describe('getEventSub', () => {
      it('should return NewEventSub for default EventSub initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventSubFormGroup(sampleWithNewData);

        const eventSub = service.getEventSub(formGroup) as any;

        expect(eventSub).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventSub for empty EventSub initial value', () => {
        const formGroup = service.createEventSubFormGroup();

        const eventSub = service.getEventSub(formGroup) as any;

        expect(eventSub).toMatchObject({});
      });

      it('should return IEventSub', () => {
        const formGroup = service.createEventSubFormGroup(sampleWithRequiredData);

        const eventSub = service.getEventSub(formGroup) as any;

        expect(eventSub).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventSub should not enable id FormControl', () => {
        const formGroup = service.createEventSubFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventSub should disable id FormControl', () => {
        const formGroup = service.createEventSubFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
