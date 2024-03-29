import { AfterViewChecked, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { IPlayerChat } from '../../../models/player-chat.model';
import { ActivatedRoute, Router } from '@angular/router';
import { FriendChatsService } from '../../services/friend-chats.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EMPTY, interval, Observable, Subscription, switchMap, tap } from 'rxjs';
import { IPlayerFriendship } from '../../models/player-friendship.model';
import { FriendsService } from '../../services/friends.service';
import { ChatValidatorService } from '../../../../shared/validators/chat-validator.service';
import { IFriendMessage } from '../../models/friend-message.model';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-friend-chats-list',
  templateUrl: './friend-chats-list.component.html',
  styleUrls: ['./friend-chats-list.component.scss'],
})
export class FriendChatsListComponent implements OnInit, AfterViewChecked, OnDestroy {
  friendshipId!: number;
  playerChats!: IPlayerChat[];
  playerFriend$!: Observable<IPlayerFriendship>;

  friendMessageForm = new FormGroup({
    message: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.maxLength(512), this.chatValidatorService.noWhitespace()],
    }),
  });

  autoRefreshSub!: Subscription;

  disableScrollDown = false;
  @ViewChild('messageContainer') private messageContainer!: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private friendChatsService: FriendChatsService,
    private friendsService: FriendsService,
    private chatValidatorService: ChatValidatorService
  ) {}

  ngOnInit(): void {
    this.friendshipId = +this.route.snapshot.params['id'];
    this.friendChatsService
      .getAllPlayerChatsByFriendshipId(this.friendshipId)
      .pipe(
        tap(playerChats => (this.playerChats = playerChats)),
        catchError(() => {
          this.redirectTo404();
          return EMPTY;
        })
      )
      .subscribe();

    this.playerFriend$ = this.friendsService.getMyPlayerFriendByFriendshipId(this.friendshipId);

    this.initAutoRefresh();
  }

  initAutoRefresh(): void {
    this.autoRefreshSub = interval(2000)
      .pipe(
        switchMap(() =>
          this.friendChatsService
            .getAllPlayerChatsByFriendshipId(this.friendshipId)
            .pipe(tap(playerChats => this.updateChatsIfChanged(playerChats)))
        )
      )
      .subscribe();
  }

  updateChatsIfChanged(playersChats: IPlayerChat[]): void {
    if (this.playerChats.length !== playersChats.length) {
      this.playerChats = playersChats;
    }
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
    const friendMessage: IFriendMessage = this.friendMessageForm.getRawValue();
    friendMessage.message = friendMessage.message.trim();

    this.friendChatsService.createFriendshipChatMessage(friendMessage, this.friendshipId).subscribe(observer => {
      if (observer.status === 201) {
        this.friendChatsService
          .getAllPlayerChatsByFriendshipId(this.friendshipId)
          .pipe(
            tap(playerChats => (this.playerChats = playerChats)),
            tap(() => (this.disableScrollDown = false)),
            tap(() => this.friendMessageForm.reset())
          )
          .subscribe();
      }
    });
  }

  redirectTo404(): void {
    this.router.navigate(['404'], { skipLocationChange: true });
  }

  ngOnDestroy(): void {
    this.autoRefreshSub.unsubscribe();
  }
}
