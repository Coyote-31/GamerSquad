import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-events-delete-modal',
  templateUrl: './events-delete-modal.component.html',
  styleUrls: ['./events-delete-modal.component.scss'],
})
export class EventsDeleteModalComponent {
  constructor(protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(): void {
    this.activeModal.close();
  }
}
