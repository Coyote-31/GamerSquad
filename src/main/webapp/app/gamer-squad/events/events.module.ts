import { NgModule } from '@angular/core';

import { SharedModule } from '../../shared/shared.module';
import { EventsRoutingModule } from './events-routing.module';
import { EventsDetailComponent } from './components/events-detail/events-detail.component';
import { EventsCreateComponent } from './components/events-create/events-create.component';
import { EventsService } from './services/events.service';
import { GamesService } from './services/games.service';

@NgModule({
  imports: [SharedModule, EventsRoutingModule],
  declarations: [EventsDetailComponent, EventsCreateComponent],
  providers: [EventsService, GamesService],
})
export class EventsModule {}
