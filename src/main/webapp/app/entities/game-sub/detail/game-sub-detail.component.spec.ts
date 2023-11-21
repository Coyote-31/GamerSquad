import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GameSubDetailComponent } from './game-sub-detail.component';

describe('GameSub Management Detail Component', () => {
  let comp: GameSubDetailComponent;
  let fixture: ComponentFixture<GameSubDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GameSubDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ gameSub: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GameSubDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GameSubDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load gameSub on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.gameSub).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
