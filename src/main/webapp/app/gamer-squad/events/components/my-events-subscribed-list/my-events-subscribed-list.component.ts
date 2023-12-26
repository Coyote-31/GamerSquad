import { Component, OnInit } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';
import { EventsService } from '../../services/events.service';

@Component({
  selector: 'app-my-events-subscribed-list',
  templateUrl: './my-events-subscribed-list.component.html',
  styleUrls: ['./my-events-subscribed-list.component.scss'],
})
export class MyEventsSubscribedListComponent implements OnInit {
  subscribedEvents!: IEventDetail[];
  afterNowEvents!: IEventDetail[];
  beforeNowEvents!: IEventDetail[];

  constructor(private eventsService: EventsService) {}

  ngOnInit(): void {
    this.eventsService.getAllEventDetailsSubscribedByUserLoggedIn().subscribe(subscribedEvents => {
      this.subscribedEvents = subscribedEvents;
      this.extractAfterNowEvents();
      this.extractBeforeNowEvents();
    });
  }

  extractAfterNowEvents(): void {
    this.afterNowEvents = this.subscribedEvents.filter(event => event.meetingDate.isAfter(Date.now()));
  }

  extractBeforeNowEvents(): void {
    this.beforeNowEvents = this.subscribedEvents.filter(event => event.meetingDate.isBefore(Date.now())).reverse();
  }
}
