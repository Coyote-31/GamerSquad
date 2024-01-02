import { Component, OnInit } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';
import { EventsService } from '../../services/events.service';
import { ActivatedRoute } from '@angular/router';
import dayjs from 'dayjs/esm';
import { AccountService } from '../../../../core/auth/account.service';
import { EventSubsService } from '../../services/event-subs.service';
import { IEventPlayer } from '../../models/event-player.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventsInviteModalComponent } from '../events-invite-modal/events-invite-modal.component';

@Component({
  selector: 'app-events-detail',
  templateUrl: './events-detail.component.html',
  styleUrls: ['./events-detail.component.scss'],
})
export class EventsDetailComponent implements OnInit {
  eventId!: number;
  event!: IEventDetail;

  userLogin!: string;
  isUserLoggedInOwner!: boolean;
  isAlreadySub!: boolean;

  players!: IEventPlayer[];

  modalRef!: NgbModalRef;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private eventsService: EventsService,
    private eventSubsService: EventSubsService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.eventId = +this.route.snapshot.params['id'];
    this.eventsService.getEventDetailByEventId(this.eventId).subscribe(eventDetail => {
      this.event = eventDetail;
      this.event.meetingDate = dayjs(this.event.meetingDate);
      this.accountService.identity().subscribe(account => (this.userLogin = account!.login));
      this.eventSubsService.isAlreadySubscribedByEventId(this.eventId).subscribe(isAlreadySub => (this.isAlreadySub = isAlreadySub));
      this.eventSubsService.getAllEventPlayersByEventId(this.eventId).subscribe(players => (this.players = players));
      this.isUserLoggedInOwner = this.userLogin === this.event.ownerLogin;
    });
  }

  onSubscribe(): void {
    this.eventSubsService.subscribeUserByEventId(this.eventId).subscribe(() => {
      this.eventSubsService.isAlreadySubscribedByEventId(this.eventId).subscribe(isAlreadySub => (this.isAlreadySub = isAlreadySub));
      this.eventSubsService.getAllEventPlayersByEventId(this.eventId).subscribe(players => (this.players = players));
    });
  }

  onUnsubscribe(): void {
    this.eventSubsService.unsubscribeUserByEventId(this.eventId).subscribe(() => {
      this.eventSubsService.isAlreadySubscribedByEventId(this.eventId).subscribe(isAlreadySub => (this.isAlreadySub = isAlreadySub));
      this.eventSubsService.getAllEventPlayersByEventId(this.eventId).subscribe(players => (this.players = players));
    });
  }

  onInviteFriendsModal(): void {
    this.modalRef = this.modalService.open(EventsInviteModalComponent, { backdrop: 'static', centered: true, size: 'lg' });
    this.eventSubsService
      .getAllFriendsForInviteByEventId(this.eventId)
      .subscribe(friends => (this.modalRef.componentInstance.friends = friends));
    this.modalRef.componentInstance.inviteEvent.subscribe((appUserId: number) => this.inviteFriend(this.eventId, appUserId));
  }

  inviteFriend(eventId: number, appUserId: number): void {
    this.eventSubsService.inviteUserByEventIdAndAppUserId(eventId, appUserId).subscribe(() => {
      this.eventSubsService.getAllEventPlayersByEventId(this.eventId).subscribe(players => (this.players = players));
      this.eventSubsService
        .getAllFriendsForInviteByEventId(this.eventId)
        .subscribe(friends => (this.modalRef.componentInstance.friends = friends));
    });
  }

  onDeletePlayer(appUserId: number): void {
    this.eventSubsService.deleteEventSubFromOwner(this.eventId, appUserId).subscribe(() => {
      this.eventSubsService.getAllEventPlayersByEventId(this.eventId).subscribe(players => (this.players = players));
    });
  }
}
