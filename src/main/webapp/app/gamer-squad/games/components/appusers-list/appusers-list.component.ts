import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GameSubsService } from '../../services/game-subs.service';
import { map } from 'rxjs/operators';
import { IPlayerFriendship } from '../../../models/player-friendship.model';

@Component({
  selector: 'app-appusers-list',
  templateUrl: './appusers-list.component.html',
  styleUrls: ['./appusers-list.component.scss'],
})
export class AppusersListComponent implements OnInit {
  @Input() gameId!: number;

  players$!: Observable<IPlayerFriendship[]>;

  constructor(private gameSubsService: GameSubsService) {}

  ngOnInit(): void {
    this.players$ = this.gameSubsService.findAllAppUsersSubToGame(this.gameId).pipe(map(players => this.sortAppUsers(players)));
  }

  sortAppUsers(players: IPlayerFriendship[]): IPlayerFriendship[] {
    return players.sort((a, b) => (a.userLogin > b.userLogin ? 1 : -1));
  }
}
