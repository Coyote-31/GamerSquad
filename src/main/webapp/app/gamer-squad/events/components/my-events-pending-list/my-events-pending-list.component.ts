import { Component, OnInit } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';
import { EventsService } from '../../services/events.service';
import { EventSubsService } from '../../services/event-subs.service';

@Component({
  selector: 'app-my-events-pending-list',
  templateUrl: './my-events-pending-list.component.html',
  styleUrls: ['./my-events-pending-list.component.scss'],
})
export class MyEventsPendingListComponent implements OnInit {
  pendingEvents!: IEventDetail[];
  afterNowEvents!: IEventDetail[];
  beforeNowEvents!: IEventDetail[];

  constructor(private eventsService: EventsService, private eventSubsService: EventSubsService) {}

  ngOnInit(): void {
    this.initEvents();
  }

  initEvents(): void {
    this.eventsService.getAllEventDetailsPendingByUserLoggedIn().subscribe(pendingEvents => {
      this.pendingEvents = pendingEvents;
      this.extractAfterNowEvents();
      this.extractBeforeNowEvents();
    });
  }

  extractAfterNowEvents(): void {
    this.afterNowEvents = this.pendingEvents.filter(event => event.meetingDate.isAfter(Date.now()));
  }

  extractBeforeNowEvents(): void {
    this.beforeNowEvents = this.pendingEvents.filter(event => event.meetingDate.isBefore(Date.now())).reverse();
  }

  onAcceptInvite(eventId: number): void {
    this.eventSubsService.acceptInviteByEventId(eventId).subscribe(() => this.initEvents());
  }

  onRefuseInvite(eventId: number): void {
    this.eventSubsService.refuseInviteByEventId(eventId).subscribe(() => this.initEvents());
  }
}
