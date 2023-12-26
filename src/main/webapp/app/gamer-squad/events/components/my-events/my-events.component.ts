import { Component } from '@angular/core';

@Component({
  selector: 'app-my-events',
  templateUrl: './my-events.component.html',
  styleUrls: ['./my-events.component.scss'],
})
export class MyEventsComponent {
  activeMenu: 'owned' | 'subscribed' | 'pending' = 'owned';

  onMenu(menu: 'owned' | 'subscribed' | 'pending'): void {
    this.activeMenu = menu;
  }
}
