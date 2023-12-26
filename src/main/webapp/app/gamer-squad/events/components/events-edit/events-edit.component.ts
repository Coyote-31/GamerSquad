import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';
import { ActivatedRoute, Router } from '@angular/router';
import { EventsService } from '../../services/events.service';
import { IEventDetail } from '../../models/event-detail.model';
import { IEventEdit } from '../../models/event-edit.model';
import { DATE_TIME_FORMAT } from '../../../../config/input.constants';

@Component({
  selector: 'app-events-edit',
  templateUrl: './events-edit.component.html',
  styleUrls: ['./events-edit.component.scss'],
})
export class EventsEditComponent implements OnInit {
  event!: IEventDetail;

  eventForm!: FormGroup;

  constructor(private route: ActivatedRoute, private router: Router, private eventsService: EventsService) {}

  ngOnInit(): void {
    this.eventsService.getEventDetailByEventId(+this.route.snapshot.params['eventId']).subscribe(event => {
      event.meetingDate = dayjs(event.meetingDate);
      this.event = event;
      this.initForm();
    });
  }

  initForm(): void {
    this.eventForm = new FormGroup({
      title: new FormControl(this.event.title, {
        nonNullable: true,
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      description: new FormControl(this.event.description, {
        nonNullable: true,
        validators: [Validators.maxLength(1024)],
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
    eventToUpdate.meetingDate = dayjs(eventToUpdate.meetingDate);

    this.eventsService.updateEvent(eventToUpdate, this.event.id).subscribe(event => {
      this.router.navigate(['events', event.id]);
    });
  }
}
