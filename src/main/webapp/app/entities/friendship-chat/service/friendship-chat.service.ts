import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFriendshipChat, NewFriendshipChat } from '../friendship-chat.model';

export type PartialUpdateFriendshipChat = Partial<IFriendshipChat> & Pick<IFriendshipChat, 'id'>;

type RestOf<T extends IFriendshipChat | NewFriendshipChat> = Omit<T, 'sendAt'> & {
  sendAt?: string | null;
};

export type RestFriendshipChat = RestOf<IFriendshipChat>;

export type NewRestFriendshipChat = RestOf<NewFriendshipChat>;

export type PartialUpdateRestFriendshipChat = RestOf<PartialUpdateFriendshipChat>;

export type EntityResponseType = HttpResponse<IFriendshipChat>;
export type EntityArrayResponseType = HttpResponse<IFriendshipChat[]>;

@Injectable({ providedIn: 'root' })
export class FriendshipChatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/friendship-chats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(friendshipChat: NewFriendshipChat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(friendshipChat);
    return this.http
      .post<RestFriendshipChat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(friendshipChat: IFriendshipChat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(friendshipChat);
    return this.http
      .put<RestFriendshipChat>(`${this.resourceUrl}/${this.getFriendshipChatIdentifier(friendshipChat)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(friendshipChat: PartialUpdateFriendshipChat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(friendshipChat);
    return this.http
      .patch<RestFriendshipChat>(`${this.resourceUrl}/${this.getFriendshipChatIdentifier(friendshipChat)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFriendshipChat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFriendshipChat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFriendshipChatIdentifier(friendshipChat: Pick<IFriendshipChat, 'id'>): number {
    return friendshipChat.id;
  }

  compareFriendshipChat(o1: Pick<IFriendshipChat, 'id'> | null, o2: Pick<IFriendshipChat, 'id'> | null): boolean {
    return o1 && o2 ? this.getFriendshipChatIdentifier(o1) === this.getFriendshipChatIdentifier(o2) : o1 === o2;
  }

  addFriendshipChatToCollectionIfMissing<Type extends Pick<IFriendshipChat, 'id'>>(
    friendshipChatCollection: Type[],
    ...friendshipChatsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const friendshipChats: Type[] = friendshipChatsToCheck.filter(isPresent);
    if (friendshipChats.length > 0) {
      const friendshipChatCollectionIdentifiers = friendshipChatCollection.map(
        friendshipChatItem => this.getFriendshipChatIdentifier(friendshipChatItem)!
      );
      const friendshipChatsToAdd = friendshipChats.filter(friendshipChatItem => {
        const friendshipChatIdentifier = this.getFriendshipChatIdentifier(friendshipChatItem);
        if (friendshipChatCollectionIdentifiers.includes(friendshipChatIdentifier)) {
          return false;
        }
        friendshipChatCollectionIdentifiers.push(friendshipChatIdentifier);
        return true;
      });
      return [...friendshipChatsToAdd, ...friendshipChatCollection];
    }
    return friendshipChatCollection;
  }

  protected convertDateFromClient<T extends IFriendshipChat | NewFriendshipChat | PartialUpdateFriendshipChat>(
    friendshipChat: T
  ): RestOf<T> {
    return {
      ...friendshipChat,
      sendAt: friendshipChat.sendAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restFriendshipChat: RestFriendshipChat): IFriendshipChat {
    return {
      ...restFriendshipChat,
      sendAt: restFriendshipChat.sendAt ? dayjs(restFriendshipChat.sendAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFriendshipChat>): HttpResponse<IFriendshipChat> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFriendshipChat[]>): HttpResponse<IFriendshipChat[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
