import { Component, Input, OnInit } from '@angular/core';
import { AccountService } from '../../../../core/auth/account.service';
import dayjs from 'dayjs/esm';
import { IEventPlayerChat } from '../../models/event-player-chat.model';

@Component({
  selector: 'app-events-chats-list-item',
  templateUrl: './events-chats-list-item.component.html',
  styleUrls: ['./events-chats-list-item.component.scss'],
})
export class EventsChatsListItemComponent implements OnInit {
  @Input() eventPlayerChat!: IEventPlayerChat;

  isSender!: boolean;

  constructor(private service: AccountService) {}

  ngOnInit(): void {
    this.service.identity().subscribe(account => {
      this.isSender = account?.login === this.eventPlayerChat.senderUserLogin;
      this.eventPlayerChat.eventChatSendAt = dayjs(this.eventPlayerChat.eventChatSendAt);
    });
  }
}
