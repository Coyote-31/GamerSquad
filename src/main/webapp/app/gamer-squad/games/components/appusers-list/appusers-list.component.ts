import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IAppUser } from '../../../../entities/app-user/app-user.model';
import { GameSubsService } from '../../services/game-subs.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-appusers-list',
  templateUrl: './appusers-list.component.html',
  styleUrls: ['./appusers-list.component.scss'],
})
export class AppusersListComponent implements OnInit {
  @Input() gameId!: number;

  appUsers$!: Observable<IAppUser[]>;

  constructor(private gameSubsService: GameSubsService) {}

  ngOnInit(): void {
    this.appUsers$ = this.gameSubsService.findAllAppUsersSubToGame(this.gameId).pipe(map(appUsers => this.sortAppUsers(appUsers)));
  }

  sortAppUsers(appUsers: IAppUser[]): IAppUser[] {
    return appUsers.sort((a, b) => {
      if (a.internalUser?.login == undefined) return 1;
      if (b.internalUser?.login == undefined) return -1;
      if (a.internalUser?.login === b.internalUser?.login) return 0;
      return a.internalUser?.login > b.internalUser?.login ? 1 : -1;
    });
  }
}
