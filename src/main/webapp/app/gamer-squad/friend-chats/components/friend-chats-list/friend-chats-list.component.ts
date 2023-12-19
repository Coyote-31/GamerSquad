import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IPlayerChat } from '../../../models/player-chat.model';
import { ActivatedRoute } from '@angular/router';
import { FriendChatsService } from '../../services/friend-chats.service';

@Component({
  selector: 'app-friend-chats-list',
  templateUrl: './friend-chats-list.component.html',
  styleUrls: ['./friend-chats-list.component.scss'],
})
export class FriendChatsListComponent implements OnInit {
  friendshipId!: number;
  playerChats$!: Observable<IPlayerChat[]>;

  constructor(private route: ActivatedRoute, private friendChatsService: FriendChatsService) {}

  ngOnInit(): void {
    this.friendshipId = +this.route.snapshot.params['id'];
    this.playerChats$ = this.friendChatsService.getAllPlayerChatsByFriendshipId(this.friendshipId);
  }
}
