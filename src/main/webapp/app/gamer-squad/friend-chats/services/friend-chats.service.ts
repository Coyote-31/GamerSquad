import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IPlayerChat } from '../../models/player-chat.model';
import { Injectable } from '@angular/core';

@Injectable()
export class FriendChatsService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/friendship-chats');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAllPlayerChatsByFriendshipId(friendshipId: number): Observable<IPlayerChat[]> {
    return this.http.get<IPlayerChat[]>(`${this.resourceUrl}/friendship/${friendshipId}`);
  }
}
