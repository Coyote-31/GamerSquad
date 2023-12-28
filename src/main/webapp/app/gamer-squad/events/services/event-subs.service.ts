import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IEventPlayer } from '../models/event-player.model';
import { IEventSub } from '../../../entities/event-sub/event-sub.model';
import { IEventFriend } from '../models/event-friend.model';

@Injectable()
export class EventSubsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/event-subs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getAllEventPlayersByEventId(eventId: number): Observable<IEventPlayer[]> {
    return this.http.get<IEventPlayer[]>(`${this.resourceUrl}/${eventId}/event-players`);
  }

  isAlreadySubscribedByEventId(eventId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.resourceUrl}/${eventId}/is-subscribed`);
  }

  subscribeUserByEventId(eventId: number): Observable<IEventSub> {
    return this.http.post<IEventSub>(`${this.resourceUrl}/${eventId}/subscribe`, {});
  }

  unsubscribeUserByEventId(eventId: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${eventId}/unsubscribe`, { observe: 'response' });
  }

  getAllFriendsForInviteByEventId(eventId: number): Observable<IEventFriend[]> {
    return this.http.get<IEventFriend[]>(`${this.resourceUrl}/${eventId}/event-friends`);
  }

  inviteUserByEventIdAndAppUserId(eventId: number, appUserId: number): Observable<IEventSub> {
    return this.http.post<IEventSub>(`${this.resourceUrl}/${eventId}/app-user/${appUserId}/invite`, {});
  }
}
