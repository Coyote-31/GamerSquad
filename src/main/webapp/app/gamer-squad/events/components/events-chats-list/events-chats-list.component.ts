import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { interval, Subscription, switchMap, tap } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IEventPlayerChat } from '../../models/event-player-chat.model';
import { EventChatsService } from '../../services/event-chats.service';
import { ChatValidatorService } from '../../../../shared/validators/chat-validator.service';

@Component({
  selector: 'app-events-chats-list',
  templateUrl: './events-chats-list.component.html',
  styleUrls: ['./events-chats-list.component.scss'],
})
export class EventsChatsListComponent implements OnInit, OnDestroy {
  @Input() eventId!: number;
  eventPlayerChats!: IEventPlayerChat[];

  eventMessageForm = new FormGroup({
    message: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.maxLength(512), this.chatValidatorService.noWhitespace()],
    }),
  });

  autoRefreshSub!: Subscription;

  constructor(
    private route: ActivatedRoute,
    private eventChatsService: EventChatsService,
    private chatValidatorService: ChatValidatorService
  ) {}

  ngOnInit(): void {
    this.eventChatsService
      .getAllEventPlayerChatsByEventId(this.eventId)
      .pipe(tap(eventPlayerChats => (this.eventPlayerChats = eventPlayerChats)))
      .subscribe();

    this.initAutoRefresh();
  }

  initAutoRefresh(): void {
    this.autoRefreshSub = interval(2000)
      .pipe(
        switchMap(() =>
          this.eventChatsService
            .getAllEventPlayerChatsByEventId(this.eventId)
            .pipe(tap(eventPlayerChats => (this.eventPlayerChats = eventPlayerChats)))
        )
      )
      .subscribe();
  }

  OnNewMessage(): void {
    this.eventChatsService.createEventChatMessage(this.eventMessageForm.getRawValue(), this.eventId).subscribe(observer => {
      if (observer.status === 201) {
        this.eventChatsService
          .getAllEventPlayerChatsByEventId(this.eventId)
          .pipe(
            tap(eventPlayerChats => (this.eventPlayerChats = eventPlayerChats)),
            tap(() => this.eventMessageForm.reset())
          )
          .subscribe();
      }
    });
  }

  ngOnDestroy(): void {
    this.autoRefreshSub.unsubscribe();
  }
}
