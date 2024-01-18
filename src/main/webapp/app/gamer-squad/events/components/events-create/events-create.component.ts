import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IGame } from '../../../../entities/game/game.model';
import { GamesService } from '../../services/games.service';
import { ActivatedRoute, Router } from '@angular/router';
import dayjs from 'dayjs/esm';
import { IEventCreate } from '../../models/event-create.model';
import { EventsService } from '../../services/events.service';
import { DATE_TIME_FORMAT } from '../../../../config/input.constants';
import { EventValidatorService } from '../../../../shared/validators/event-validator.service';

/**
 * Type that converts some properties for forms.
 */
type EventFormRawValue = Omit<IEventCreate, 'meetingDate'> & {
  meetingDate?: string | null;
};

@Component({
  selector: 'app-events-create',
  templateUrl: './events-create.component.html',
  styleUrls: ['./events-create.component.scss'],
})
export class EventsCreateComponent implements OnInit {
  game!: IGame;

  eventForm = new FormGroup({
    title: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.maxLength(255), this.eventValidatorService.noWhitespace()],
    }),
    description: new FormControl('', {
      nonNullable: true,
      validators: [Validators.maxLength(1024), this.eventValidatorService.noWhitespace()],
    }),
    meetingDate: new FormControl(null, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    isPrivate: new FormControl(false, {
      nonNullable: true,
      validators: [Validators.required],
    }),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventsService: EventsService,
    private gamesService: GamesService,
    private eventValidatorService: EventValidatorService
  ) {}

  ngOnInit(): void {
    this.gamesService.find(+this.route.snapshot.params['gameId']).subscribe({
      next: game => (this.game = game),
      error: () => this.redirectTo404(),
    });
  }

  OnSubmit(): void {
    const rawEventToCreate: EventFormRawValue = this.eventForm.getRawValue();
    const eventToCreate: IEventCreate = this.convertEventRawToIEventCreate(rawEventToCreate);

    this.eventsService.createEvent(eventToCreate, this.game.id).subscribe(event => {
      this.router.navigate(['events', event.id]);
    });
  }

  convertEventRawToIEventCreate(rawEvent: EventFormRawValue): IEventCreate {
    return {
      ...rawEvent,
      title: rawEvent.title.trim(),
      description: rawEvent.description ? rawEvent.description.trim() : null,
      meetingDate: dayjs(rawEvent.meetingDate, DATE_TIME_FORMAT),
    };
  }

  cancel(): void {
    this.router.navigate(['games', this.game.id]);
  }

  redirectTo404(): void {
    this.router.navigate(['404'], { skipLocationChange: true });
  }
}
