import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IEventDetail } from '../../models/event-detail.model';

@Injectable({
  providedIn: 'root',
})
export class EventsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getAllEventDetailsPublicByGameId(gameId: number): Observable<IEventDetail[]> {
    return this.http.get<IEventDetail[]>(`${this.resourceUrl}/games/${gameId}/event-details`);
  }
}
