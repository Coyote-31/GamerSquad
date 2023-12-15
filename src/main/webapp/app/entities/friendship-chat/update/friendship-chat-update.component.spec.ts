import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FriendshipChatFormService } from './friendship-chat-form.service';
import { FriendshipChatService } from '../service/friendship-chat.service';
import { IFriendshipChat } from '../friendship-chat.model';
import { IFriendship } from 'app/entities/friendship/friendship.model';
import { FriendshipService } from 'app/entities/friendship/service/friendship.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';

import { FriendshipChatUpdateComponent } from './friendship-chat-update.component';

describe('FriendshipChat Management Update Component', () => {
  let comp: FriendshipChatUpdateComponent;
  let fixture: ComponentFixture<FriendshipChatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let friendshipChatFormService: FriendshipChatFormService;
  let friendshipChatService: FriendshipChatService;
  let friendshipService: FriendshipService;
  let appUserService: AppUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FriendshipChatUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FriendshipChatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FriendshipChatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    friendshipChatFormService = TestBed.inject(FriendshipChatFormService);
    friendshipChatService = TestBed.inject(FriendshipChatService);
    friendshipService = TestBed.inject(FriendshipService);
    appUserService = TestBed.inject(AppUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Friendship query and add missing value', () => {
      const friendshipChat: IFriendshipChat = { id: 456 };
      const friendship: IFriendship = { id: 96736 };
      friendshipChat.friendship = friendship;

      const friendshipCollection: IFriendship[] = [{ id: 23285 }];
      jest.spyOn(friendshipService, 'query').mockReturnValue(of(new HttpResponse({ body: friendshipCollection })));
      const additionalFriendships = [friendship];
      const expectedCollection: IFriendship[] = [...additionalFriendships, ...friendshipCollection];
      jest.spyOn(friendshipService, 'addFriendshipToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ friendshipChat });
      comp.ngOnInit();

      expect(friendshipService.query).toHaveBeenCalled();
      expect(friendshipService.addFriendshipToCollectionIfMissing).toHaveBeenCalledWith(
        friendshipCollection,
        ...additionalFriendships.map(expect.objectContaining)
      );
      expect(comp.friendshipsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AppUser query and add missing value', () => {
      const friendshipChat: IFriendshipChat = { id: 456 };
      const sender: IAppUser = { id: 58212 };
      friendshipChat.sender = sender;

      const appUserCollection: IAppUser[] = [{ id: 8934 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [sender];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ friendshipChat });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining)
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const friendshipChat: IFriendshipChat = { id: 456 };
      const friendship: IFriendship = { id: 11662 };
      friendshipChat.friendship = friendship;
      const sender: IAppUser = { id: 6021 };
      friendshipChat.sender = sender;

      activatedRoute.data = of({ friendshipChat });
      comp.ngOnInit();

      expect(comp.friendshipsSharedCollection).toContain(friendship);
      expect(comp.appUsersSharedCollection).toContain(sender);
      expect(comp.friendshipChat).toEqual(friendshipChat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFriendshipChat>>();
      const friendshipChat = { id: 123 };
      jest.spyOn(friendshipChatFormService, 'getFriendshipChat').mockReturnValue(friendshipChat);
      jest.spyOn(friendshipChatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ friendshipChat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: friendshipChat }));
      saveSubject.complete();

      // THEN
      expect(friendshipChatFormService.getFriendshipChat).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(friendshipChatService.update).toHaveBeenCalledWith(expect.objectContaining(friendshipChat));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFriendshipChat>>();
      const friendshipChat = { id: 123 };
      jest.spyOn(friendshipChatFormService, 'getFriendshipChat').mockReturnValue({ id: null });
      jest.spyOn(friendshipChatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ friendshipChat: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: friendshipChat }));
      saveSubject.complete();

      // THEN
      expect(friendshipChatFormService.getFriendshipChat).toHaveBeenCalled();
      expect(friendshipChatService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFriendshipChat>>();
      const friendshipChat = { id: 123 };
      jest.spyOn(friendshipChatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ friendshipChat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(friendshipChatService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFriendship', () => {
      it('Should forward to friendshipService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(friendshipService, 'compareFriendship');
        comp.compareFriendship(entity, entity2);
        expect(friendshipService.compareFriendship).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAppUser', () => {
      it('Should forward to appUserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(appUserService, 'compareAppUser');
        comp.compareAppUser(entity, entity2);
        expect(appUserService.compareAppUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
