import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventChatFormService, EventChatFormGroup } from './event-chat-form.service';
import { IEventChat } from '../event-chat.model';
import { EventChatService } from '../service/event-chat.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';

@Component({
  selector: 'jhi-event-chat-update',
  templateUrl: './event-chat-update.component.html',
})
export class EventChatUpdateComponent implements OnInit {
  isSaving = false;
  eventChat: IEventChat | null = null;

  eventsSharedCollection: IEvent[] = [];
  appUsersSharedCollection: IAppUser[] = [];

  editForm: EventChatFormGroup = this.eventChatFormService.createEventChatFormGroup();

  constructor(
    protected eventChatService: EventChatService,
    protected eventChatFormService: EventChatFormService,
    protected eventService: EventService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEvent = (o1: IEvent | null, o2: IEvent | null): boolean => this.eventService.compareEvent(o1, o2);

  compareAppUser = (o1: IAppUser | null, o2: IAppUser | null): boolean => this.appUserService.compareAppUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventChat }) => {
      this.eventChat = eventChat;
      if (eventChat) {
        this.updateForm(eventChat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventChat = this.eventChatFormService.getEventChat(this.editForm);
    if (eventChat.id !== null) {
      this.subscribeToSaveResponse(this.eventChatService.update(eventChat));
    } else {
      this.subscribeToSaveResponse(this.eventChatService.create(eventChat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventChat>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(eventChat: IEventChat): void {
    this.eventChat = eventChat;
    this.eventChatFormService.resetForm(this.editForm, eventChat);

    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing<IEvent>(this.eventsSharedCollection, eventChat.event);
    this.appUsersSharedCollection = this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(
      this.appUsersSharedCollection,
      eventChat.appUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing<IEvent>(events, this.eventChat?.event)))
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));

    this.appUserService
      .query()
      .pipe(map((res: HttpResponse<IAppUser[]>) => res.body ?? []))
      .pipe(map((appUsers: IAppUser[]) => this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(appUsers, this.eventChat?.appUser)))
      .subscribe((appUsers: IAppUser[]) => (this.appUsersSharedCollection = appUsers));
  }
}
