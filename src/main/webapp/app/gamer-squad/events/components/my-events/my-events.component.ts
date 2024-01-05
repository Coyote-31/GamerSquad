import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-my-events',
  templateUrl: './my-events.component.html',
  styleUrls: ['./my-events.component.scss'],
})
export class MyEventsComponent implements OnInit {
  activeMenu: 'owned' | 'subscribed' | 'pending' = 'owned';

  routeMenu: 'owned' | 'subscribed' | 'pending' | undefined | null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.routeMenu = this.route.snapshot.params['menu'];
    if (this.routeMenu === 'owned' || this.routeMenu === 'subscribed' || this.routeMenu === 'pending') {
      this.activeMenu = this.routeMenu;
    }
  }

  onMenu(menu: 'owned' | 'subscribed' | 'pending'): void {
    this.activeMenu = menu;
  }
}
