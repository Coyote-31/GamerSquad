import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GameSubComponent } from './list/game-sub.component';
import { GameSubDetailComponent } from './detail/game-sub-detail.component';
import { GameSubUpdateComponent } from './update/game-sub-update.component';
import { GameSubDeleteDialogComponent } from './delete/game-sub-delete-dialog.component';
import { GameSubRoutingModule } from './route/game-sub-routing.module';

@NgModule({
  imports: [SharedModule, GameSubRoutingModule],
  declarations: [GameSubComponent, GameSubDetailComponent, GameSubUpdateComponent, GameSubDeleteDialogComponent],
})
export class GameSubModule {}
