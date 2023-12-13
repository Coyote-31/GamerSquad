import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../../services/friends.service';
import { IPlayerFriendship } from '../../../models/player-friendship.model';
import { Observable, tap } from 'rxjs';

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrls: ['./friends-list.component.scss'],
})
export class FriendsListComponent implements OnInit {
  friends$!: Observable<IPlayerFriendship[]>;

  friends!: IPlayerFriendship[];
  pendingFriends!: IPlayerFriendship[];
  receivedFriends!: IPlayerFriendship[];

  constructor(private friendsService: FriendsService) {}

  ngOnInit(): void {
    this.getPlayers();
  }

  getPlayers(): void {
    this.friends$ = this.friendsService.getAllMyPlayersFriends();
    this.populateLists();
  }

  populateLists(): void {
    this.friends$
      .pipe(
        tap(friends => {
          this.friends = friends.filter(friend => friend.accepted === true);
          this.pendingFriends = friends.filter(friend => friend.accepted === false && friend.received);
          this.receivedFriends = friends.filter(friend => friend.accepted === false && friend.owned);
        })
      )
      .subscribe();
  }

  onFriendshipAccept(appUserId: number): void {
    this.friendsService
      .acceptFriendshipDemand(appUserId)
      .pipe(tap(() => this.getPlayers()))
      .subscribe();
  }

  onFriendshipDelete(appUserId: number): void {
    this.friendsService
      .deleteFriendship(appUserId)
      .pipe(tap(() => this.getPlayers()))
      .subscribe();
  }
}
