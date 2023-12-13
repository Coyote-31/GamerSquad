import { Component, Input, OnInit } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { GameSubsService } from '../../services/game-subs.service';
import { IPlayerFriendship } from '../../../models/player-friendship.model';
import { FriendshipsService } from '../../services/friendships.service';

@Component({
  selector: 'app-players-list',
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.scss'],
})
export class PlayersListComponent implements OnInit {
  @Input() gameId!: number;

  players$!: Observable<IPlayerFriendship[]>;

  friends!: IPlayerFriendship[];
  pendingFriends!: IPlayerFriendship[];
  notFriends!: IPlayerFriendship[];

  constructor(private gameSubsService: GameSubsService, private friendshipsService: FriendshipsService) {}

  ngOnInit(): void {
    this.getPlayers();
  }

  getPlayers(): void {
    this.players$ = this.gameSubsService.findAllPlayersSubToGame(this.gameId);
    this.populateLists();
  }

  populateLists(): void {
    this.players$
      .pipe(
        tap(players => {
          this.friends = players.filter(player => player.friendshipId && player.accepted === true);
          this.pendingFriends = players.filter(player => player.friendshipId && player.accepted === false);
          this.notFriends = players.filter(player => !player.friendshipId);
        })
      )
      .subscribe();
  }

  onFriendshipDemandEvent(appUserId: number): void {
    this.friendshipsService
      .createFriendshipDemand(appUserId)
      .pipe(tap(() => this.getPlayers()))
      .subscribe();
  }
}
