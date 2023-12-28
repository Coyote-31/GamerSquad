import { NgModule } from '@angular/core';

import { SharedModule } from '../../shared/shared.module';
import { EventsRoutingModule } from './events-routing.module';
import { EventsDetailComponent } from './components/events-detail/events-detail.component';
import { EventsCreateComponent } from './components/events-create/events-create.component';
import { EventsEditComponent } from './components/events-edit/events-edit.component';
import { MyEventsComponent } from './components/my-events/my-events.component';
import { MyEventsOwnedListComponent } from './components/my-events-owned-list/my-events-owned-list.component';
import { MyEventsOwnedListItemComponent } from './components/my-events-owned-list-item/my-events-owned-list-item.component';
import { MyEventsSubscribedListComponent } from './components/my-events-subscribed-list/my-events-subscribed-list.component';
import { MyEventsSubscribedListItemComponent } from './components/my-events-subscribed-list-item/my-events-subscribed-list-item.component';
import { MyEventsPendingListComponent } from './components/my-events-pending-list/my-events-pending-list.component';
import { MyEventsPendingListItemComponent } from './components/my-events-pending-list-item/my-events-pending-list-item.component';
import { EventsService } from './services/events.service';
import { GamesService } from './services/games.service';
import { EventsPlayersListComponent } from './components/events-players-list/events-players-list.component';
import { EventsPlayersListItemComponent } from './components/events-players-list-item/events-players-list-item.component';
import { EventSubsService } from './services/event-subs.service';
import { EventsInviteModalComponent } from './components/events-invite-modal/events-invite-modal.component';
import { EventsInviteFriendsListComponent } from './components/events-invite-friends-list/events-invite-friends-list.component';
import { EventsInviteFriendsListItemComponent } from './components/events-invite-friends-list-item/events-invite-friends-list-item.component';

@NgModule({
  imports: [SharedModule, EventsRoutingModule],
  declarations: [
    EventsDetailComponent,
    EventsCreateComponent,
    EventsEditComponent,
    MyEventsComponent,
    MyEventsOwnedListComponent,
    MyEventsOwnedListItemComponent,
    MyEventsSubscribedListComponent,
    MyEventsSubscribedListItemComponent,
    MyEventsPendingListComponent,
    MyEventsPendingListItemComponent,
    EventsPlayersListComponent,
    EventsPlayersListItemComponent,
    EventsInviteModalComponent,
    EventsInviteFriendsListComponent,
    EventsInviteFriendsListItemComponent,
  ],
  providers: [EventsService, EventSubsService, GamesService],
})
export class EventsModule {}
