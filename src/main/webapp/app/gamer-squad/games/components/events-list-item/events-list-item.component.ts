import { Component, Input, OnInit } from '@angular/core';
import { IEventDetail } from '../../../models/event-detail.model';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'app-events-list-item',
  templateUrl: './events-list-item.component.html',
  styleUrls: ['./events-list-item.component.scss'],
})
export class EventsListItemComponent implements OnInit {
  @Input() event!: IEventDetail;

  ngOnInit(): void {
    this.event.meetingDate = dayjs(this.event.meetingDate);
  }
}
