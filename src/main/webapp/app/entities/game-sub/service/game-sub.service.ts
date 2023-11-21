import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGameSub, NewGameSub } from '../game-sub.model';

export type PartialUpdateGameSub = Partial<IGameSub> & Pick<IGameSub, 'id'>;

export type EntityResponseType = HttpResponse<IGameSub>;
export type EntityArrayResponseType = HttpResponse<IGameSub[]>;

@Injectable({ providedIn: 'root' })
export class GameSubService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/game-subs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(gameSub: NewGameSub): Observable<EntityResponseType> {
    return this.http.post<IGameSub>(this.resourceUrl, gameSub, { observe: 'response' });
  }

  update(gameSub: IGameSub): Observable<EntityResponseType> {
    return this.http.put<IGameSub>(`${this.resourceUrl}/${this.getGameSubIdentifier(gameSub)}`, gameSub, { observe: 'response' });
  }

  partialUpdate(gameSub: PartialUpdateGameSub): Observable<EntityResponseType> {
    return this.http.patch<IGameSub>(`${this.resourceUrl}/${this.getGameSubIdentifier(gameSub)}`, gameSub, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGameSub>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGameSub[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGameSubIdentifier(gameSub: Pick<IGameSub, 'id'>): number {
    return gameSub.id;
  }

  compareGameSub(o1: Pick<IGameSub, 'id'> | null, o2: Pick<IGameSub, 'id'> | null): boolean {
    return o1 && o2 ? this.getGameSubIdentifier(o1) === this.getGameSubIdentifier(o2) : o1 === o2;
  }

  addGameSubToCollectionIfMissing<Type extends Pick<IGameSub, 'id'>>(
    gameSubCollection: Type[],
    ...gameSubsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const gameSubs: Type[] = gameSubsToCheck.filter(isPresent);
    if (gameSubs.length > 0) {
      const gameSubCollectionIdentifiers = gameSubCollection.map(gameSubItem => this.getGameSubIdentifier(gameSubItem)!);
      const gameSubsToAdd = gameSubs.filter(gameSubItem => {
        const gameSubIdentifier = this.getGameSubIdentifier(gameSubItem);
        if (gameSubCollectionIdentifiers.includes(gameSubIdentifier)) {
          return false;
        }
        gameSubCollectionIdentifiers.push(gameSubIdentifier);
        return true;
      });
      return [...gameSubsToAdd, ...gameSubCollection];
    }
    return gameSubCollection;
  }
}
