import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IPlayerFriendship } from '../../models/player-friendship.model';

@Injectable()
export class FriendsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/friendships');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getAllMyPlayersFriends(): Observable<IPlayerFriendship[]> {
    return this.http.get<IPlayerFriendship[]>(`${this.resourceUrl}/players`);
  }

  acceptFriendshipDemand(appUserId: number): Observable<IPlayerFriendship> {
    return this.http.patch<IPlayerFriendship>(`${this.resourceUrl}/app-user/${appUserId}/accept`, {});
  }

  deleteFriendship(appUserId: number): Observable<void> {
    return this.http.delete<void>(`${this.resourceUrl}/app-user/${appUserId}/delete`);
  }
}
