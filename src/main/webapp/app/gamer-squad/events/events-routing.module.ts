import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventsDetailComponent } from './events-detail/events-detail.component';

const routes: Routes = [
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
