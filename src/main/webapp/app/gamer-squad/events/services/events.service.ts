import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IEventDetail } from '../models/event-detail.model';
import { IEventCreate } from '../models/event-create.model';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';
import { IEventEdit } from '../models/event-edit.model';

@Injectable()
export class EventsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/events');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getEventDetailByEventId(eventId: number): Observable<IEventDetail> {
    return this.http.get<IEventDetail>(`${this.resourceUrl}/${eventId}/event-detail`);
  }

  getAllEventDetailsOwnedByUserLoggedIn(): Observable<IEventDetail[]> {
    return this.http.get<IEventDetail[]>(`${this.resourceUrl}/my-events/owned`).pipe(
      map(events =>
        events.map(event => {
          event.meetingDate = dayjs(event.meetingDate);
          return event;
        })
      )
    );
  }

  getAllEventDetailsSubscribedByUserLoggedIn(): Observable<IEventDetail[]> {
    return this.http.get<IEventDetail[]>(`${this.resourceUrl}/my-events/subscribed`).pipe(
      map(events =>
        events.map(event => {
          event.meetingDate = dayjs(event.meetingDate);
          return event;
        })
      )
    );
  }

  getAllEventDetailsPendingByUserLoggedIn(): Observable<IEventDetail[]> {
    return this.http.get<IEventDetail[]>(`${this.resourceUrl}/my-events/pending`).pipe(
      map(events =>
        events.map(event => {
          event.meetingDate = dayjs(event.meetingDate);
          return event;
        })
      )
    );
  }

  createEvent(event: IEventCreate, gameId: number): Observable<IEventDetail> {
    return this.http.post<IEventDetail>(`${this.resourceUrl}/game/${gameId}/create`, event);
  }

  updateEvent(event: IEventEdit, eventId: number): Observable<IEventDetail> {
    return this.http.put<IEventDetail>(`${this.resourceUrl}/${eventId}/update`, event);
  }
}
