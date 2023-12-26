import { NgModule } from '@angular/core';

import { SharedModule } from '../../shared/shared.module';
import { EventsRoutingModule } from './events-routing.module';
import { EventsDetailComponent } from './components/events-detail/events-detail.component';
import { EventsCreateComponent } from './components/events-create/events-create.component';
import { EventsService } from './services/events.service';
import { GamesService } from './services/games.service';
import { MyEventsComponent } from './components/my-events/my-events.component';
import { MyEventsOwnedListComponent } from './components/my-events-owned-list/my-events-owned-list.component';
import { MyEventsOwnedListItemComponent } from './components/my-events-owned-list-item/my-events-owned-list-item.component';
import { MyEventsSubscribedListComponent } from './components/my-events-subscribed-list/my-events-subscribed-list.component';
import { MyEventsSubscribedListItemComponent } from './components/my-events-subscribed-list-item/my-events-subscribed-list-item.component';
import { MyEventsPendingListComponent } from './components/my-events-pending-list/my-events-pending-list.component';
import { MyEventsPendingListItemComponent } from './components/my-events-pending-list-item/my-events-pending-list-item.component';

@NgModule({
  imports: [SharedModule, EventsRoutingModule],
  declarations: [
    EventsDetailComponent,
    EventsCreateComponent,
    MyEventsComponent,
    MyEventsOwnedListComponent,
    MyEventsOwnedListItemComponent,
    MyEventsSubscribedListComponent,
    MyEventsSubscribedListItemComponent,
    MyEventsPendingListComponent,
    MyEventsPendingListItemComponent,
  ],
  providers: [EventsService, GamesService],
})
export class EventsModule {}
