import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IEventPlayer } from '../../models/event-player.model';

@Component({
  selector: 'app-events-players-list',
  templateUrl: './events-players-list.component.html',
  styleUrls: ['./events-players-list.component.scss'],
})
export class EventsPlayersListComponent {
  @Input() players!: IEventPlayer[];
  @Input() isUserLoggedInOwner!: boolean;

  @Output() deletePlayerEvent = new EventEmitter<number>();

  onDeletePlayer(appUserId: number): void {
    this.deletePlayerEvent.emit(appUserId);
  }
}
