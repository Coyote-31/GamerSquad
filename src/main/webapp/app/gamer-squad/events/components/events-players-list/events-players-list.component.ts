import { Component, Input, OnInit } from '@angular/core';
import { EventSubsService } from '../../services/event-subs.service';
import { IEventPlayer } from '../../models/event-player.model';

@Component({
  selector: 'app-events-players-list',
  templateUrl: './events-players-list.component.html',
  styleUrls: ['./events-players-list.component.scss'],
})
export class EventsPlayersListComponent implements OnInit {
  @Input() eventId!: number;

  players!: IEventPlayer[];

  constructor(private eventSubsService: EventSubsService) {}

  ngOnInit(): void {
    this.eventSubsService.getAllEventPlayersByEventId(this.eventId).subscribe(players => (this.players = players));
  }
}
