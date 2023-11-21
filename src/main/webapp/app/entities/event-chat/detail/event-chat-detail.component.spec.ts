import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventChatDetailComponent } from './event-chat-detail.component';

describe('EventChat Management Detail Component', () => {
  let comp: EventChatDetailComponent;
  let fixture: ComponentFixture<EventChatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventChatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventChat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventChatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventChatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventChat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventChat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
