import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventSubFormService } from './event-sub-form.service';
import { EventSubService } from '../service/event-sub.service';
import { IEventSub } from '../event-sub.model';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';

import { EventSubUpdateComponent } from './event-sub-update.component';

describe('EventSub Management Update Component', () => {
  let comp: EventSubUpdateComponent;
  let fixture: ComponentFixture<EventSubUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventSubFormService: EventSubFormService;
  let eventSubService: EventSubService;
  let eventService: EventService;
  let appUserService: AppUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventSubUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EventSubUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventSubUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventSubFormService = TestBed.inject(EventSubFormService);
    eventSubService = TestBed.inject(EventSubService);
    eventService = TestBed.inject(EventService);
    appUserService = TestBed.inject(AppUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Event query and add missing value', () => {
      const eventSub: IEventSub = { id: 456 };
      const event: IEvent = { id: 73621 };
      eventSub.event = event;

      const eventCollection: IEvent[] = [{ id: 44445 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEvents = [event];
      const expectedCollection: IEvent[] = [...additionalEvents, ...eventCollection];
      jest.spyOn(eventService, 'addEventToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventSub });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEvents.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AppUser query and add missing value', () => {
      const eventSub: IEventSub = { id: 456 };
      const appUser: IAppUser = { id: 36658 };
      eventSub.appUser = appUser;

      const appUserCollection: IAppUser[] = [{ id: 74627 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [appUser];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventSub });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining)
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventSub: IEventSub = { id: 456 };
      const event: IEvent = { id: 88308 };
      eventSub.event = event;
      const appUser: IAppUser = { id: 87911 };
      eventSub.appUser = appUser;

      activatedRoute.data = of({ eventSub });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.appUsersSharedCollection).toContain(appUser);
      expect(comp.eventSub).toEqual(eventSub);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSub>>();
      const eventSub = { id: 123 };
      jest.spyOn(eventSubFormService, 'getEventSub').mockReturnValue(eventSub);
      jest.spyOn(eventSubService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventSub });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventSub }));
      saveSubject.complete();

      // THEN
      expect(eventSubFormService.getEventSub).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventSubService.update).toHaveBeenCalledWith(expect.objectContaining(eventSub));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSub>>();
      const eventSub = { id: 123 };
      jest.spyOn(eventSubFormService, 'getEventSub').mockReturnValue({ id: null });
      jest.spyOn(eventSubService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventSub: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventSub }));
      saveSubject.complete();

      // THEN
      expect(eventSubFormService.getEventSub).toHaveBeenCalled();
      expect(eventSubService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSub>>();
      const eventSub = { id: 123 };
      jest.spyOn(eventSubService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventSub });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventSubService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEvent', () => {
      it('Should forward to eventService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventService, 'compareEvent');
        comp.compareEvent(entity, entity2);
        expect(eventService.compareEvent).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAppUser', () => {
      it('Should forward to appUserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(appUserService, 'compareAppUser');
        comp.compareAppUser(entity, entity2);
        expect(appUserService.compareAppUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
