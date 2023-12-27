import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IEventPlayer } from '../models/event-player.model';

@Injectable()
export class EventSubsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/event-subs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getAllEventPlayersByEventId(eventId: number): Observable<IEventPlayer[]> {
    return this.http.get<IEventPlayer[]>(`${this.resourceUrl}/${eventId}/event-players`);
  }
}
