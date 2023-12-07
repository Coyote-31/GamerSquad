import { NgModule } from '@angular/core';

import { GamesRoutingModule } from './games-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { GamesListComponent } from './components/games-list/games-list.component';
import { GamesListItemComponent } from './components/games-list-item/games-list-item.component';
import { GamesDetailComponent } from './components/games-detail/games-detail.component';
import { AppusersListComponent } from './components/appusers-list/appusers-list.component';
import { AppusersListItemComponent } from './components/appusers-list-item/appusers-list-item.component';

@NgModule({
  imports: [SharedModule, GamesRoutingModule],
  declarations: [GamesListComponent, GamesListItemComponent, GamesDetailComponent, AppusersListComponent, AppusersListItemComponent],
})
export class GamesModule {}
