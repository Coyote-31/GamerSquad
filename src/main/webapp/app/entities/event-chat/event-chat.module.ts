import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventChatComponent } from './list/event-chat.component';
import { EventChatDetailComponent } from './detail/event-chat-detail.component';
import { EventChatUpdateComponent } from './update/event-chat-update.component';
import { EventChatDeleteDialogComponent } from './delete/event-chat-delete-dialog.component';
import { EventChatRoutingModule } from './route/event-chat-routing.module';

@NgModule({
  imports: [SharedModule, EventChatRoutingModule],
  declarations: [EventChatComponent, EventChatDetailComponent, EventChatUpdateComponent, EventChatDeleteDialogComponent],
})
export class EventChatModule {}
