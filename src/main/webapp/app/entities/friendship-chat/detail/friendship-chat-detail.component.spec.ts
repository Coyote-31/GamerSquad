import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FriendshipChatDetailComponent } from './friendship-chat-detail.component';

describe('FriendshipChat Management Detail Component', () => {
  let comp: FriendshipChatDetailComponent;
  let fixture: ComponentFixture<FriendshipChatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FriendshipChatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ friendshipChat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FriendshipChatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FriendshipChatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load friendshipChat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.friendshipChat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
