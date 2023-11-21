import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFriendshipChat } from '../friendship-chat.model';

@Component({
  selector: 'jhi-friendship-chat-detail',
  templateUrl: './friendship-chat-detail.component.html',
})
export class FriendshipChatDetailComponent implements OnInit {
  friendshipChat: IFriendshipChat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ friendshipChat }) => {
      this.friendshipChat = friendshipChat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
