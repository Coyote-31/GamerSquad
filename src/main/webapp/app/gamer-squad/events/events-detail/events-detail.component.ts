import { Component, OnInit } from '@angular/core';
import { IEventDetail } from '../models/event-detail.model';
import { EventsService } from '../services/events.service';
import { ActivatedRoute } from '@angular/router';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'app-events-detail',
  templateUrl: './events-detail.component.html',
  styleUrls: ['./events-detail.component.scss'],
})
export class EventsDetailComponent implements OnInit {
  eventId!: number;
  event!: IEventDetail;

  constructor(private route: ActivatedRoute, private eventsService: EventsService) {}

  ngOnInit(): void {
    this.eventId = +this.route.snapshot.params['id'];
    this.eventsService.getEventDetailByEventId(this.eventId).subscribe(eventDetail => {
      this.event = eventDetail;
      this.event.meetingDate = dayjs(this.event.meetingDate);
    });
  }
}
