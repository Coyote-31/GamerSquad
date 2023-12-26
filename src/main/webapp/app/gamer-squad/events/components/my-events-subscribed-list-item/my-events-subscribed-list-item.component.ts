import { Component, Input } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';

@Component({
  selector: 'app-my-events-subscribed-list-item',
  templateUrl: './my-events-subscribed-list-item.component.html',
  styleUrls: ['./my-events-subscribed-list-item.component.scss'],
})
export class MyEventsSubscribedListItemComponent {
  @Input() event!: IEventDetail;
}
