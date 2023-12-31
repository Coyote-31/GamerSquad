import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventsDetailComponent } from './components/events-detail/events-detail.component';
import { EventsCreateComponent } from './components/events-create/events-create.component';
import { MyEventsComponent } from './components/my-events/my-events.component';
import { EventsEditComponent } from './components/events-edit/events-edit.component';

const routes: Routes = [
  {
    path: 'my-events',
    title: 'Gamer Squad - Mes évènements',
    component: MyEventsComponent,
  },
  {
    path: 'my-events/:menu',
    title: 'Gamer Squad - Mes évènements',
    component: MyEventsComponent,
  },
  {
    path: 'game/:gameId/create',
    title: 'Gamer Squad - Nouvel évènement',
    component: EventsCreateComponent,
  },
  {
    path: ':eventId/edit',
    title: "Gamer Squad - Éditer l'évènement",
    component: EventsEditComponent,
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
