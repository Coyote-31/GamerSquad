import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../../services/friends.service';
import { IPlayerFriendship } from '../../../models/player-friendship.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrls: ['./friends-list.component.scss'],
})
export class FriendsListComponent implements OnInit {
  friends$!: Observable<IPlayerFriendship[]>;

  constructor(private friendsService: FriendsService) {}

  ngOnInit(): void {
    this.friends$ = this.friendsService.getAllMyPlayersFriends();
  }
}
