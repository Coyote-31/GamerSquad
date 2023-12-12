import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FriendsRoutingModule } from './friends-routing.module';
import { FriendsListComponent } from './components/friends-list/friends-list.component';
import { FriendsListItemComponent } from './components/friends-list-item/friends-list-item.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [CommonModule, FriendsRoutingModule, SharedModule],
  declarations: [FriendsListComponent, FriendsListItemComponent],
})
export class FriendsModule {}
