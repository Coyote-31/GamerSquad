import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventChat, NewEventChat } from '../event-chat.model';

export type PartialUpdateEventChat = Partial<IEventChat> & Pick<IEventChat, 'id'>;

type RestOf<T extends IEventChat | NewEventChat> = Omit<T, 'sendAt'> & {
  sendAt?: string | null;
};

export type RestEventChat = RestOf<IEventChat>;

export type NewRestEventChat = RestOf<NewEventChat>;

export type PartialUpdateRestEventChat = RestOf<PartialUpdateEventChat>;

export type EntityResponseType = HttpResponse<IEventChat>;
export type EntityArrayResponseType = HttpResponse<IEventChat[]>;

@Injectable({ providedIn: 'root' })
export class EventChatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-chats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventChat: NewEventChat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventChat);
    return this.http
      .post<RestEventChat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(eventChat: IEventChat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventChat);
    return this.http
      .put<RestEventChat>(`${this.resourceUrl}/${this.getEventChatIdentifier(eventChat)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(eventChat: PartialUpdateEventChat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventChat);
    return this.http
      .patch<RestEventChat>(`${this.resourceUrl}/${this.getEventChatIdentifier(eventChat)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEventChat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEventChat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventChatIdentifier(eventChat: Pick<IEventChat, 'id'>): number {
    return eventChat.id;
  }

  compareEventChat(o1: Pick<IEventChat, 'id'> | null, o2: Pick<IEventChat, 'id'> | null): boolean {
    return o1 && o2 ? this.getEventChatIdentifier(o1) === this.getEventChatIdentifier(o2) : o1 === o2;
  }

  addEventChatToCollectionIfMissing<Type extends Pick<IEventChat, 'id'>>(
    eventChatCollection: Type[],
    ...eventChatsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventChats: Type[] = eventChatsToCheck.filter(isPresent);
    if (eventChats.length > 0) {
      const eventChatCollectionIdentifiers = eventChatCollection.map(eventChatItem => this.getEventChatIdentifier(eventChatItem)!);
      const eventChatsToAdd = eventChats.filter(eventChatItem => {
        const eventChatIdentifier = this.getEventChatIdentifier(eventChatItem);
        if (eventChatCollectionIdentifiers.includes(eventChatIdentifier)) {
          return false;
        }
        eventChatCollectionIdentifiers.push(eventChatIdentifier);
        return true;
      });
      return [...eventChatsToAdd, ...eventChatCollection];
    }
    return eventChatCollection;
  }

  protected convertDateFromClient<T extends IEventChat | NewEventChat | PartialUpdateEventChat>(eventChat: T): RestOf<T> {
    return {
      ...eventChat,
      sendAt: eventChat.sendAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEventChat: RestEventChat): IEventChat {
    return {
      ...restEventChat,
      sendAt: restEventChat.sendAt ? dayjs(restEventChat.sendAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEventChat>): HttpResponse<IEventChat> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEventChat[]>): HttpResponse<IEventChat[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
