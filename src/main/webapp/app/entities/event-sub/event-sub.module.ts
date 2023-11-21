import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventSubComponent } from './list/event-sub.component';
import { EventSubDetailComponent } from './detail/event-sub-detail.component';
import { EventSubUpdateComponent } from './update/event-sub-update.component';
import { EventSubDeleteDialogComponent } from './delete/event-sub-delete-dialog.component';
import { EventSubRoutingModule } from './route/event-sub-routing.module';

@NgModule({
  imports: [SharedModule, EventSubRoutingModule],
  declarations: [EventSubComponent, EventSubDetailComponent, EventSubUpdateComponent, EventSubDeleteDialogComponent],
})
export class EventSubModule {}
