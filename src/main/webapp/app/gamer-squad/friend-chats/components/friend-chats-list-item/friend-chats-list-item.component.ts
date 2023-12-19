import { Component, Input, OnInit } from '@angular/core';
import { IPlayerChat } from '../../../models/player-chat.model';
import { AccountService } from '../../../../core/auth/account.service';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'app-friend-chats-list-item',
  templateUrl: './friend-chats-list-item.component.html',
  styleUrls: ['./friend-chats-list-item.component.scss'],
})
export class FriendChatsListItemComponent implements OnInit {
  @Input() playerChat!: IPlayerChat;
  isSender!: boolean;

  constructor(private service: AccountService) {}

  ngOnInit(): void {
    this.service.identity().subscribe(account => {
      this.isSender = account?.login === this.playerChat.senderUserLogin;
      this.playerChat.friendshipChatSendAt = dayjs(this.playerChat.friendshipChatSendAt);
    });
  }
}
