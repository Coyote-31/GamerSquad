import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventChatFormService } from './event-chat-form.service';
import { EventChatService } from '../service/event-chat.service';
import { IEventChat } from '../event-chat.model';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';

import { EventChatUpdateComponent } from './event-chat-update.component';

describe('EventChat Management Update Component', () => {
  let comp: EventChatUpdateComponent;
  let fixture: ComponentFixture<EventChatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventChatFormService: EventChatFormService;
  let eventChatService: EventChatService;
  let eventService: EventService;
  let appUserService: AppUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventChatUpdateComponent],
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
      .overrideTemplate(EventChatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventChatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventChatFormService = TestBed.inject(EventChatFormService);
    eventChatService = TestBed.inject(EventChatService);
    eventService = TestBed.inject(EventService);
    appUserService = TestBed.inject(AppUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Event query and add missing value', () => {
      const eventChat: IEventChat = { id: 456 };
      const event: IEvent = { id: 46900 };
      eventChat.event = event;

      const eventCollection: IEvent[] = [{ id: 38407 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEvents = [event];
      const expectedCollection: IEvent[] = [...additionalEvents, ...eventCollection];
      jest.spyOn(eventService, 'addEventToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventChat });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEvents.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AppUser query and add missing value', () => {
      const eventChat: IEventChat = { id: 456 };
      const appUser: IAppUser = { id: 92591 };
      eventChat.appUser = appUser;

      const appUserCollection: IAppUser[] = [{ id: 47847 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [appUser];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventChat });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining)
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventChat: IEventChat = { id: 456 };
      const event: IEvent = { id: 41529 };
      eventChat.event = event;
      const appUser: IAppUser = { id: 72934 };
      eventChat.appUser = appUser;

      activatedRoute.data = of({ eventChat });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.appUsersSharedCollection).toContain(appUser);
      expect(comp.eventChat).toEqual(eventChat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventChat>>();
      const eventChat = { id: 123 };
      jest.spyOn(eventChatFormService, 'getEventChat').mockReturnValue(eventChat);
      jest.spyOn(eventChatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventChat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventChat }));
      saveSubject.complete();

      // THEN
      expect(eventChatFormService.getEventChat).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventChatService.update).toHaveBeenCalledWith(expect.objectContaining(eventChat));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventChat>>();
      const eventChat = { id: 123 };
      jest.spyOn(eventChatFormService, 'getEventChat').mockReturnValue({ id: null });
      jest.spyOn(eventChatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventChat: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventChat }));
      saveSubject.complete();

      // THEN
      expect(eventChatFormService.getEventChat).toHaveBeenCalled();
      expect(eventChatService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventChat>>();
      const eventChat = { id: 123 };
      jest.spyOn(eventChatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventChat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventChatService.update).toHaveBeenCalled();
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
