import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IPlayerFriendship } from '../../models/player-friendship.model';

@Injectable({
  providedIn: 'root',
})
export class FriendshipsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/friendships');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  createFriendshipDemand(appUserId: number): Observable<IPlayerFriendship> {
    return this.http.post<IPlayerFriendship>(`${this.resourceUrl}/app-user/${appUserId}/demand`, {});
  }
}
