import { Component, Input, OnInit } from '@angular/core';
import { IAppUser } from '../../../../entities/app-user/app-user.model';

@Component({
  selector: 'app-appusers-list-item',
  templateUrl: './appusers-list-item.component.html',
  styleUrls: ['./appusers-list-item.component.scss'],
})
export class AppusersListItemComponent implements OnInit {
  @Input() appUser!: IAppUser;

  constructor() {}

  ngOnInit(): void {}
}
