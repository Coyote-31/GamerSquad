import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { IPlayerChat } from '../../../models/player-chat.model';
import { ActivatedRoute } from '@angular/router';
import { FriendChatsService } from '../../services/friend-chats.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { tap } from 'rxjs';

@Component({
  selector: 'app-friend-chats-list',
  templateUrl: './friend-chats-list.component.html',
  styleUrls: ['./friend-chats-list.component.scss'],
})
export class FriendChatsListComponent implements OnInit, AfterViewChecked {
  friendshipId!: number;
  playerChats!: IPlayerChat[];

  friendMessageForm = new FormGroup({
    message: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.maxLength(512)],
    }),
  });

  disableScrollDown = false;
  @ViewChild('messageContainer') private messageContainer!: ElementRef;

  constructor(private route: ActivatedRoute, private friendChatsService: FriendChatsService) {}

  ngOnInit(): void {
    this.friendshipId = +this.route.snapshot.params['id'];
    this.friendChatsService.getAllPlayerChatsByFriendshipId(this.friendshipId).subscribe(playerChats => (this.playerChats = playerChats));
  }

  ngAfterViewChecked(): void {
    this.scrollToBottomCheckDisable();
  }

  scrollToBottomCheckDisable(): void {
    if (this.disableScrollDown) {
      return;
    }
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
    } catch (err) {
      console.error(err);
    }
  }

  onScroll(): void {
    const element = this.messageContainer.nativeElement;
    const atBottom = element.scrollHeight - element.scrollTop === element.clientHeight;
    this.disableScrollDown = !atBottom;
  }

  OnNewMessage(): void {
    this.friendChatsService.createFriendshipChatMessage(this.friendMessageForm.getRawValue(), this.friendshipId).subscribe(observer => {
      if (observer.status === 201) {
        this.friendChatsService
          .getAllPlayerChatsByFriendshipId(this.friendshipId)
          .pipe(
            tap(playerChats => (this.playerChats = playerChats)),
            tap(() => (this.disableScrollDown = false))
          )
          .subscribe();
      }
    });
  }
}
