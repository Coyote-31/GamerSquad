import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GamesListComponent } from './components/games-list/games-list.component';
import { GamesDetailComponent } from './components/games-detail/games-detail.component';

const routes: Routes = [
  {
    path: ':id',
    component: GamesDetailComponent,
  },
  {
    path: '',
    title: 'GamerSquad - Games',
    component: GamesListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GamesRoutingModule {}
