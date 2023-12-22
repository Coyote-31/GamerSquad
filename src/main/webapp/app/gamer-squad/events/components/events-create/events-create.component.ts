import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IGame } from '../../../../entities/game/game.model';
import { GamesService } from '../../services/games.service';
import { ActivatedRoute, Router } from '@angular/router';
import dayjs from 'dayjs/esm';
import { IEventCreate } from '../../models/event-create.model';
import { EventsService } from '../../services/events.service';

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
      validators: [Validators.required, Validators.maxLength(255)],
    }),
    description: new FormControl('', {
      nonNullable: true,
      validators: [Validators.maxLength(1024)],
    }),
    meetingDate: new FormControl(dayjs(), {
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
    private gamesService: GamesService
  ) {}

  ngOnInit(): void {
    this.gamesService.find(+this.route.snapshot.params['gameId']).subscribe(game => (this.game = game));
  }

  OnSubmit(): void {
    const eventToCreate: IEventCreate = this.eventForm.getRawValue();
    eventToCreate.meetingDate = dayjs(eventToCreate.meetingDate);

    this.eventsService.createEvent(eventToCreate, this.game.id).subscribe(event => {
      this.router.navigate(['events', event.id]);
    });
  }
}
