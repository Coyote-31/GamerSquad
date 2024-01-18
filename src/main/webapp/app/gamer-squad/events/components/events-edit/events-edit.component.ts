import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';
import { ActivatedRoute, Router } from '@angular/router';
import { EventsService } from '../../services/events.service';
import { IEventDetail } from '../../models/event-detail.model';
import { IEventEdit } from '../../models/event-edit.model';
import { DATE_TIME_FORMAT } from '../../../../config/input.constants';
import { EventValidatorService } from '../../../../shared/validators/event-validator.service';

@Component({
  selector: 'app-events-edit',
  templateUrl: './events-edit.component.html',
  styleUrls: ['./events-edit.component.scss'],
})
export class EventsEditComponent implements OnInit {
  event!: IEventDetail;

  eventForm!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventsService: EventsService,
    private eventValidatorService: EventValidatorService
  ) {}

  ngOnInit(): void {
    this.eventsService.getEventDetailByEventId(+this.route.snapshot.params['eventId']).subscribe({
      next: event => {
        event.meetingDate = dayjs(event.meetingDate);
        this.event = event;
        this.initForm();
      },
      error: () => this.redirectTo404(),
    });
  }

  initForm(): void {
    this.eventForm = new FormGroup({
      title: new FormControl(this.event.title, {
        nonNullable: true,
        validators: [Validators.required, Validators.maxLength(255), this.eventValidatorService.noWhitespace()],
      }),
      description: new FormControl(this.event.description, {
        nonNullable: true,
        validators: [Validators.maxLength(1024), this.eventValidatorService.noWhitespace()],
      }),
      meetingDate: new FormControl(this.event.meetingDate.format(DATE_TIME_FORMAT), {
        nonNullable: true,
        validators: [Validators.required],
      }),
      isPrivate: new FormControl(this.event.private, {
        nonNullable: true,
        validators: [Validators.required],
      }),
    });
  }

  OnSubmit(): void {
    const eventToUpdate: IEventEdit = this.eventForm.getRawValue();
    eventToUpdate.title = eventToUpdate.title.trim();
    eventToUpdate.description = eventToUpdate.description ? eventToUpdate.description.trim() : null;
    eventToUpdate.meetingDate = dayjs(eventToUpdate.meetingDate);

    this.eventsService.updateEvent(eventToUpdate, this.event.id).subscribe(event => {
      this.router.navigate(['events', event.id]);
    });
  }

  cancel(): void {
    this.router.navigate(['events', this.event.id]);
  }

  redirectTo404(): void {
    this.router.navigate(['404'], { skipLocationChange: true });
  }
}
