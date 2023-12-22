import { NgModule } from '@angular/core';

import { EventsRoutingModule } from './events-routing.module';
import { EventsDetailComponent } from './events-detail/events-detail.component';
import { EventsService } from './services/events.service';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [SharedModule, EventsRoutingModule],
  declarations: [EventsDetailComponent],
  providers: [EventsService],
})
export class EventsModule {}
