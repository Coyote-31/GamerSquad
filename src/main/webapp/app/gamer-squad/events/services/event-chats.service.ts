import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IEventPlayerChat } from '../models/event-player-chat.model';
import { IEventMessage } from '../models/event-message.model';

@Injectable()
export class EventChatsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/event-chats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getAllEventPlayerChatsByEventId(eventId: number): Observable<IEventPlayerChat[]> {
    return this.http.get<IEventPlayerChat[]>(`${this.resourceUrl}/event/${eventId}`);
  }

  createEventChatMessage(message: IEventMessage, eventId: number): Observable<HttpResponse<{}>> {
    return this.http.post(`${this.resourceUrl}/event/${eventId}`, message, { observe: 'response' });
  }
}
