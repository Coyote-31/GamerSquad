import { Component, Input } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';

@Component({
  selector: 'app-my-events-owned-list-item',
  templateUrl: './my-events-owned-list-item.component.html',
  styleUrls: ['./my-events-owned-list-item.component.scss'],
})
export class MyEventsOwnedListItemComponent {
  @Input() event!: IEventDetail;
}
