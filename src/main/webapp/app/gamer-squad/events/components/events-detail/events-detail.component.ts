import { Component, OnInit } from '@angular/core';
import { IEventDetail } from '../../models/event-detail.model';
import { EventsService } from '../../services/events.service';
import { ActivatedRoute, Router } from '@angular/router';
import dayjs from 'dayjs/esm';
import { AccountService } from '../../../../core/auth/account.service';
import { EventSubsService } from '../../services/event-subs.service';
import { IEventPlayer } from '../../models/event-player.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventsInviteModalComponent } from '../events-invite-modal/events-invite-modal.component';
import { EventsDeleteModalComponent } from '../events-delete-modal/events-delete-modal.component';
import { switchMap } from 'rxjs';

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
  isAlreadyAccepted!: boolean;

  players!: IEventPlayer[];

  inviteFriendsModalRef!: NgbModalRef;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
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
      this.refreshIsAlready();
      this.refreshPlayers();
      this.isUserLoggedInOwner = this.userLogin === this.event.ownerLogin;
    });
  }

  refreshIsAlready(): void {
    this.eventSubsService.isAlreadySubscribedByEventId(this.eventId).subscribe(isAlreadySub => (this.isAlreadySub = isAlreadySub));
    this.eventSubsService
      .isAlreadyAcceptedByEventId(this.eventId)
      .subscribe(isAlreadyAccepted => (this.isAlreadyAccepted = isAlreadyAccepted));
  }

  refreshPlayers(): void {
    this.eventSubsService.getAllEventPlayersByEventId(this.eventId).subscribe(players => (this.players = players));
  }

  onSubscribe(): void {
    this.eventSubsService.subscribeUserByEventId(this.eventId).subscribe(() => {
      this.refreshIsAlready();
      this.refreshPlayers();
    });
  }

  onUnsubscribe(): void {
    this.eventSubsService.unsubscribeUserByEventId(this.eventId).subscribe(() => {
      this.refreshIsAlready();
      this.refreshPlayers();
    });
  }

  onInviteFriendsModal(): void {
    this.inviteFriendsModalRef = this.modalService.open(EventsInviteModalComponent, { backdrop: 'static', centered: true, size: 'lg' });
    this.eventSubsService
      .getAllFriendsForInviteByEventId(this.eventId)
      .subscribe(friends => (this.inviteFriendsModalRef.componentInstance.friends = friends));
    this.inviteFriendsModalRef.componentInstance.inviteEvent.subscribe((appUserId: number) => this.inviteFriend(this.eventId, appUserId));
  }

  inviteFriend(eventId: number, appUserId: number): void {
    this.eventSubsService.inviteUserByEventIdAndAppUserId(eventId, appUserId).subscribe(() => {
      this.refreshPlayers();
      this.eventSubsService
        .getAllFriendsForInviteByEventId(this.eventId)
        .subscribe(friends => (this.inviteFriendsModalRef.componentInstance.friends = friends));
    });
  }

  onDeletePlayer(appUserId: number): void {
    this.eventSubsService.deleteEventSubFromOwner(this.eventId, appUserId).subscribe(() => {
      this.refreshPlayers();
    });
  }

  onDeleteEventModal(): void {
    const deleteEventModalRef = this.modalService.open(EventsDeleteModalComponent, {
      backdrop: 'static',
      centered: true,
      windowClass: 'gs-modal',
    });
    deleteEventModalRef.closed
      .pipe(switchMap(() => this.eventsService.deleteEventByIdFromOwner(this.eventId)))
      .subscribe({ next: () => this.navigateToMyEvents() });
  }

  navigateToMyEvents(): void {
    this.router.navigate(['/events', 'my-events']);
  }

  navigateToMyEventsPending(): void {
    this.router.navigate(['/events', 'my-events', 'pending']);
  }

  onAcceptInvite(): void {
    this.eventSubsService.acceptInviteByEventId(this.eventId).subscribe(() => {
      this.refreshIsAlready();
      this.refreshPlayers();
    });
  }

  onRefuseInvite(): void {
    this.eventSubsService.refuseInviteByEventId(this.eventId).subscribe(() => this.navigateToMyEventsPending());
  }
}
