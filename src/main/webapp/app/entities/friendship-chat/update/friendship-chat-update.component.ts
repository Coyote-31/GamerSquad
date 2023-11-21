import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FriendshipChatFormService, FriendshipChatFormGroup } from './friendship-chat-form.service';
import { IFriendshipChat } from '../friendship-chat.model';
import { FriendshipChatService } from '../service/friendship-chat.service';
import { IFriendship } from 'app/entities/friendship/friendship.model';
import { FriendshipService } from 'app/entities/friendship/service/friendship.service';

@Component({
  selector: 'jhi-friendship-chat-update',
  templateUrl: './friendship-chat-update.component.html',
})
export class FriendshipChatUpdateComponent implements OnInit {
  isSaving = false;
  friendshipChat: IFriendshipChat | null = null;

  friendshipsSharedCollection: IFriendship[] = [];

  editForm: FriendshipChatFormGroup = this.friendshipChatFormService.createFriendshipChatFormGroup();

  constructor(
    protected friendshipChatService: FriendshipChatService,
    protected friendshipChatFormService: FriendshipChatFormService,
    protected friendshipService: FriendshipService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFriendship = (o1: IFriendship | null, o2: IFriendship | null): boolean => this.friendshipService.compareFriendship(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ friendshipChat }) => {
      this.friendshipChat = friendshipChat;
      if (friendshipChat) {
        this.updateForm(friendshipChat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const friendshipChat = this.friendshipChatFormService.getFriendshipChat(this.editForm);
    if (friendshipChat.id !== null) {
      this.subscribeToSaveResponse(this.friendshipChatService.update(friendshipChat));
    } else {
      this.subscribeToSaveResponse(this.friendshipChatService.create(friendshipChat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFriendshipChat>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(friendshipChat: IFriendshipChat): void {
    this.friendshipChat = friendshipChat;
    this.friendshipChatFormService.resetForm(this.editForm, friendshipChat);

    this.friendshipsSharedCollection = this.friendshipService.addFriendshipToCollectionIfMissing<IFriendship>(
      this.friendshipsSharedCollection,
      friendshipChat.friendship
    );
  }

  protected loadRelationshipsOptions(): void {
    this.friendshipService
      .query()
      .pipe(map((res: HttpResponse<IFriendship[]>) => res.body ?? []))
      .pipe(
        map((friendships: IFriendship[]) =>
          this.friendshipService.addFriendshipToCollectionIfMissing<IFriendship>(friendships, this.friendshipChat?.friendship)
        )
      )
      .subscribe((friendships: IFriendship[]) => (this.friendshipsSharedCollection = friendships));
  }
}
