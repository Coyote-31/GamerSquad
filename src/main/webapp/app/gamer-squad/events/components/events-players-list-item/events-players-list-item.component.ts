import { Component, Input } from '@angular/core';
import { IEventPlayer } from '../../models/event-player.model';

@Component({
  selector: 'app-events-players-list-item',
  templateUrl: './events-players-list-item.component.html',
  styleUrls: ['./events-players-list-item.component.scss'],
})
export class EventsPlayersListItemComponent {
  @Input() player!: IEventPlayer;
}
