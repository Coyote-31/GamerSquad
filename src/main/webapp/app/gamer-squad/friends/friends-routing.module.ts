import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FriendsListComponent } from './components/friends-list/friends-list.component';

const routes: Routes = [
  {
    path: '',
    title: 'GamerSquad - Amis',
    component: FriendsListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FriendsRoutingModule {}
