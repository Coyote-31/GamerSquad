import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGameSub } from '../game-sub.model';
import { GameSubService } from '../service/game-sub.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './game-sub-delete-dialog.component.html',
})
export class GameSubDeleteDialogComponent {
  gameSub?: IGameSub;

  constructor(protected gameSubService: GameSubService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gameSubService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
