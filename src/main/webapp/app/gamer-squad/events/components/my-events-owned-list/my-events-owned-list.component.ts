import { Component, OnInit } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';
import { EventsService } from '../../services/events.service';

@Component({
  selector: 'app-my-events-owned-list',
  templateUrl: './my-events-owned-list.component.html',
  styleUrls: ['./my-events-owned-list.component.scss'],
})
export class MyEventsOwnedListComponent implements OnInit {
  ownedEvents!: IEventDetail[];
  afterNowEvents!: IEventDetail[];
  beforeNowEvents!: IEventDetail[];

  constructor(private eventsService: EventsService) {}

  ngOnInit(): void {
    this.eventsService.getAllEventDetailsOwnedByUserLoggedIn().subscribe(ownedEvents => {
      this.ownedEvents = ownedEvents;
      this.extractAfterNowEvents();
      this.extractBeforeNowEvents();
    });
  }

  extractAfterNowEvents(): void {
    this.afterNowEvents = this.ownedEvents.filter(event => event.meetingDate.isAfter(Date.now()));
  }

  extractBeforeNowEvents(): void {
    this.beforeNowEvents = this.ownedEvents.filter(event => event.meetingDate.isBefore(Date.now())).reverse();
  }
}
