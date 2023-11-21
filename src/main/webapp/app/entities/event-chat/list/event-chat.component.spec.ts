import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EventChatService } from '../service/event-chat.service';

import { EventChatComponent } from './event-chat.component';

describe('EventChat Management Component', () => {
  let comp: EventChatComponent;
  let fixture: ComponentFixture<EventChatComponent>;
  let service: EventChatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'event-chat', component: EventChatComponent }]), HttpClientTestingModule],
      declarations: [EventChatComponent],
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
      .overrideTemplate(EventChatComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventChatComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EventChatService);

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
    expect(comp.eventChats?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to eventChatService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getEventChatIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getEventChatIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
