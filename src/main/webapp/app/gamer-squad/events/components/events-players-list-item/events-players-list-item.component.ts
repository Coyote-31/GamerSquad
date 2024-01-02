import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IEventPlayer } from '../../models/event-player.model';
import { tap } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EventsPlayersDeleteModalComponent } from '../events-players-delete-modal/events-players-delete-modal.component';

@Component({
  selector: 'app-events-players-list-item',
  templateUrl: './events-players-list-item.component.html',
  styleUrls: ['./events-players-list-item.component.scss'],
})
export class EventsPlayersListItemComponent {
  @Input() player!: IEventPlayer;
  @Input() isUserLoggedInOwner!: boolean;

  @Output() deletePlayerEvent = new EventEmitter<number>();

  constructor(protected modalService: NgbModal) {}

  onDeletePlayer(): void {
    const modalRef = this.modalService.open(EventsPlayersDeleteModalComponent, {
      backdrop: 'static',
      centered: true,
      windowClass: 'gs-modal',
    });
    modalRef.componentInstance.player = this.player;
    modalRef.closed.pipe(tap(() => this.deletePlayerEvent.emit(this.player.appUserId))).subscribe();
  }
}
