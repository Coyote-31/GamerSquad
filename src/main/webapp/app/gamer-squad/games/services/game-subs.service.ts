import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IGameSub } from '../../../entities/game-sub/game-sub.model';
import { IAppUser } from '../../../entities/app-user/app-user.model';

@Injectable({
  providedIn: 'root',
})
export class GameSubsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/game-subs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  isSubscribed(gameId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.resourceUrl}/is-subscribed/${gameId}`);
  }

  subscribe(gameId: number): Observable<IGameSub> {
    return this.http.post<IGameSub>(`${this.resourceUrl}/subscribe/${gameId}`, {});
  }

  unsubscribe(gameId: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/unsubscribe/${gameId}`, { observe: 'response' });
  }

  findAllAppUsersSubToGame(gameId: number): Observable<IAppUser[]> {
    return this.http.get<IAppUser[]>(`${this.resourceUrl}/game/${gameId}/app-users`);
  }
}
