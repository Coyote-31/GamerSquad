import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventSub } from '../event-sub.model';

@Component({
  selector: 'jhi-event-sub-detail',
  templateUrl: './event-sub-detail.component.html',
})
export class EventSubDetailComponent implements OnInit {
  eventSub: IEventSub | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventSub }) => {
      this.eventSub = eventSub;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
