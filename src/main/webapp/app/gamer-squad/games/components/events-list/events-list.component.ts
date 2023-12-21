import { Component, Input, OnInit } from '@angular/core';
import { IEventDetail } from '../../../models/event-detail.model';
import { EventsService } from '../../services/events.service';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.scss'],
})
export class EventsListComponent implements OnInit {
  @Input() gameId!: number;

  events!: IEventDetail[];

  constructor(private eventsService: EventsService) {}

  ngOnInit(): void {
    this.eventsService.getAllEventDetailsPublicByGameId(this.gameId).subscribe(eventsDetails => (this.events = eventsDetails));
  }
}
