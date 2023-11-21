import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GameSubService } from '../service/game-sub.service';

import { GameSubComponent } from './game-sub.component';

describe('GameSub Management Component', () => {
  let comp: GameSubComponent;
  let fixture: ComponentFixture<GameSubComponent>;
  let service: GameSubService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'game-sub', component: GameSubComponent }]), HttpClientTestingModule],
      declarations: [GameSubComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(GameSubComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GameSubComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GameSubService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.gameSubs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to gameSubService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getGameSubIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getGameSubIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
