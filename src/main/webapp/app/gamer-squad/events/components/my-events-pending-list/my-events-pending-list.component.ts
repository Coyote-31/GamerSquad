import { Component, OnInit } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';
import { EventsService } from '../../services/events.service';

@Component({
  selector: 'app-my-events-pending-list',
  templateUrl: './my-events-pending-list.component.html',
  styleUrls: ['./my-events-pending-list.component.scss'],
})
export class MyEventsPendingListComponent implements OnInit {
  pendingEvents!: IEventDetail[];
  afterNowEvents!: IEventDetail[];
  beforeNowEvents!: IEventDetail[];

  constructor(private eventsService: EventsService) {}

  ngOnInit(): void {
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
}
