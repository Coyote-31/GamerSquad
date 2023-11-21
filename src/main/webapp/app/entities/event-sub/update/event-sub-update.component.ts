import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventSubFormService, EventSubFormGroup } from './event-sub-form.service';
import { IEventSub } from '../event-sub.model';
import { EventSubService } from '../service/event-sub.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';

@Component({
  selector: 'jhi-event-sub-update',
  templateUrl: './event-sub-update.component.html',
})
export class EventSubUpdateComponent implements OnInit {
  isSaving = false;
  eventSub: IEventSub | null = null;

  eventsSharedCollection: IEvent[] = [];
  appUsersSharedCollection: IAppUser[] = [];

  editForm: EventSubFormGroup = this.eventSubFormService.createEventSubFormGroup();

  constructor(
    protected eventSubService: EventSubService,
    protected eventSubFormService: EventSubFormService,
    protected eventService: EventService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEvent = (o1: IEvent | null, o2: IEvent | null): boolean => this.eventService.compareEvent(o1, o2);

  compareAppUser = (o1: IAppUser | null, o2: IAppUser | null): boolean => this.appUserService.compareAppUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventSub }) => {
      this.eventSub = eventSub;
      if (eventSub) {
        this.updateForm(eventSub);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventSub = this.eventSubFormService.getEventSub(this.editForm);
    if (eventSub.id !== null) {
      this.subscribeToSaveResponse(this.eventSubService.update(eventSub));
    } else {
      this.subscribeToSaveResponse(this.eventSubService.create(eventSub));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventSub>>): void {
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

  protected updateForm(eventSub: IEventSub): void {
    this.eventSub = eventSub;
    this.eventSubFormService.resetForm(this.editForm, eventSub);

    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing<IEvent>(this.eventsSharedCollection, eventSub.event);
    this.appUsersSharedCollection = this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(
      this.appUsersSharedCollection,
      eventSub.appUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing<IEvent>(events, this.eventSub?.event)))
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));

    this.appUserService
      .query()
      .pipe(map((res: HttpResponse<IAppUser[]>) => res.body ?? []))
      .pipe(map((appUsers: IAppUser[]) => this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(appUsers, this.eventSub?.appUser)))
      .subscribe((appUsers: IAppUser[]) => (this.appUsersSharedCollection = appUsers));
  }
}
