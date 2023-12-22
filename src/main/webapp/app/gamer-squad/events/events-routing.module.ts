import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventsDetailComponent } from './components/events-detail/events-detail.component';
import { EventsCreateComponent } from './components/events-create/events-create.component';

const routes: Routes = [
  {
    path: 'game/:gameId/create',
    title: 'Gamer Squad - Nouvel évènement',
    component: EventsCreateComponent,
  },
  {
    path: ':id',
    title: 'Gamer Squad - Évènement',
    component: EventsDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EventsRoutingModule {}
