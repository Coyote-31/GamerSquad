import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventSub, NewEventSub } from '../event-sub.model';

export type PartialUpdateEventSub = Partial<IEventSub> & Pick<IEventSub, 'id'>;

export type EntityResponseType = HttpResponse<IEventSub>;
export type EntityArrayResponseType = HttpResponse<IEventSub[]>;

@Injectable({ providedIn: 'root' })
export class EventSubService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-subs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventSub: NewEventSub): Observable<EntityResponseType> {
    return this.http.post<IEventSub>(this.resourceUrl, eventSub, { observe: 'response' });
  }

  update(eventSub: IEventSub): Observable<EntityResponseType> {
    return this.http.put<IEventSub>(`${this.resourceUrl}/${this.getEventSubIdentifier(eventSub)}`, eventSub, { observe: 'response' });
  }

  partialUpdate(eventSub: PartialUpdateEventSub): Observable<EntityResponseType> {
    return this.http.patch<IEventSub>(`${this.resourceUrl}/${this.getEventSubIdentifier(eventSub)}`, eventSub, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventSub>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventSub[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventSubIdentifier(eventSub: Pick<IEventSub, 'id'>): number {
    return eventSub.id;
  }

  compareEventSub(o1: Pick<IEventSub, 'id'> | null, o2: Pick<IEventSub, 'id'> | null): boolean {
    return o1 && o2 ? this.getEventSubIdentifier(o1) === this.getEventSubIdentifier(o2) : o1 === o2;
  }

  addEventSubToCollectionIfMissing<Type extends Pick<IEventSub, 'id'>>(
    eventSubCollection: Type[],
    ...eventSubsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventSubs: Type[] = eventSubsToCheck.filter(isPresent);
    if (eventSubs.length > 0) {
      const eventSubCollectionIdentifiers = eventSubCollection.map(eventSubItem => this.getEventSubIdentifier(eventSubItem)!);
      const eventSubsToAdd = eventSubs.filter(eventSubItem => {
        const eventSubIdentifier = this.getEventSubIdentifier(eventSubItem);
        if (eventSubCollectionIdentifiers.includes(eventSubIdentifier)) {
          return false;
        }
        eventSubCollectionIdentifiers.push(eventSubIdentifier);
        return true;
      });
      return [...eventSubsToAdd, ...eventSubCollection];
    }
    return eventSubCollection;
  }
}
