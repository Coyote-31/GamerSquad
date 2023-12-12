import { Component, Input, OnInit } from '@angular/core';
import { IPlayerFriendship } from '../../../models/player-friendship.model';

@Component({
  selector: 'app-friends-list-item',
  templateUrl: './friends-list-item.component.html',
  styleUrls: ['./friends-list-item.component.scss'],
})
export class FriendsListItemComponent implements OnInit {
  @Input() friend!: IPlayerFriendship;

  constructor() {}

  ngOnInit(): void {}
}
