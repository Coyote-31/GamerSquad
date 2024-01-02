import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IEventPlayer } from '../../models/event-player.model';

@Component({
  selector: 'app-events-players-delete-modal',
  templateUrl: './events-players-delete-modal.component.html',
  styleUrls: ['./events-players-delete-modal.component.scss'],
})
export class EventsPlayersDeleteModalComponent {
  player!: IEventPlayer;

  constructor(protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(): void {
    this.activeModal.close();
  }
}
