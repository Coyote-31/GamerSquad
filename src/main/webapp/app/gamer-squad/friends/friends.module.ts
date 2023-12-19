import { NgModule } from '@angular/core';

import { FriendsRoutingModule } from './friends-routing.module';
import { FriendsListComponent } from './components/friends-list/friends-list.component';
import { FriendsListItemComponent } from './components/friends-list-item/friends-list-item.component';
import { SharedModule } from '../../shared/shared.module';
import { FriendsDeleteDialogComponent } from './components/friends-delete-dialog/friends-delete-dialog.component';

@NgModule({
  imports: [SharedModule, FriendsRoutingModule],
  declarations: [FriendsListComponent, FriendsListItemComponent, FriendsDeleteDialogComponent],
})
export class FriendsModule {}
