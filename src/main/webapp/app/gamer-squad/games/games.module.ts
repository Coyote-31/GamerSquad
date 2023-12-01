import { NgModule } from '@angular/core';

import { GamesRoutingModule } from './games-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { GamesListComponent } from './components/games-list/games-list.component';
import { GamesListItemComponent } from './components/games-list-item/games-list-item.component';

@NgModule({
  imports: [SharedModule, GamesRoutingModule],
  declarations: [GamesListComponent, GamesListItemComponent],
})
export class GamesModule {}
