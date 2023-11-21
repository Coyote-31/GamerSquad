import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventSubDetailComponent } from './event-sub-detail.component';

describe('EventSub Management Detail Component', () => {
  let comp: EventSubDetailComponent;
  let fixture: ComponentFixture<EventSubDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventSubDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventSub: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventSubDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventSubDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventSub on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventSub).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
