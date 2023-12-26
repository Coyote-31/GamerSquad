import { Component, Input } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';

@Component({
  selector: 'app-my-events-pending-list-item',
  templateUrl: './my-events-pending-list-item.component.html',
  styleUrls: ['./my-events-pending-list-item.component.scss'],
})
export class MyEventsPendingListItemComponent {
  @Input() event!: IEventDetail;
}
