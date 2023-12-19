import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IPlayerFriendship } from '../models/player-friendship.model';

@Injectable()
export class FriendsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/friendships');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getMyPlayerFriendByFriendshipId(friendshipId: number): Observable<IPlayerFriendship> {
    return this.http.get<IPlayerFriendship>(`${this.resourceUrl}/${friendshipId}/player`);
  }
}
