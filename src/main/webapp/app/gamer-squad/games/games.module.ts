import { NgModule } from '@angular/core';

import { GamesRoutingModule } from './games-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { GamesListComponent } from './components/games-list/games-list.component';
import { GamesListItemComponent } from './components/games-list-item/games-list-item.component';
import { GamesDetailComponent } from './components/games-detail/games-detail.component';
import { PlayersListComponent } from './components/players-list/players-list.component';
import { PlayersListItemComponent } from './components/players-list-item/players-list-item.component';

@NgModule({
  imports: [SharedModule, GamesRoutingModule],
  declarations: [GamesListComponent, GamesListItemComponent, GamesDetailComponent, PlayersListComponent, PlayersListItemComponent],
})
export class GamesModule {}
