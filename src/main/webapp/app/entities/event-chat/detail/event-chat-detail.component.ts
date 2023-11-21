import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventChat } from '../event-chat.model';

@Component({
  selector: 'jhi-event-chat-detail',
  templateUrl: './event-chat-detail.component.html',
})
export class EventChatDetailComponent implements OnInit {
  eventChat: IEventChat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventChat }) => {
      this.eventChat = eventChat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
