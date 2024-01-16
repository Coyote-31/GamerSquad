package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.EventSub;
import com.coyote.gamersquad.domain.dto.projection.EventFriendDTO;
import com.coyote.gamersquad.domain.dto.projection.EventPlayerDTO;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventSubRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipRepositoryExtended;
import com.coyote.gamersquad.service.EventSubService;
import com.coyote.gamersquad.service.dto.EventSubDTO;
import com.coyote.gamersquad.service.errors.AppUserNotFoundException;
import com.coyote.gamersquad.service.errors.EventNotFoundException;
import com.coyote.gamersquad.service.errors.EventSubNotFoundException;
import com.coyote.gamersquad.service.errors.ForbiddenException;
import com.coyote.gamersquad.service.mapper.EventSubMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link EventSub}.
 */
@Service
@Transactional
public class EventSubServiceExtended extends EventSubService {

    private final Logger log = LoggerFactory.getLogger(EventSubServiceExtended.class);

    EventSubRepositoryExtended eventSubRepository;

    EventSubMapper eventSubMapper;

    EventRepositoryExtended eventRepository;

    AppUserRepositoryExtended appUserRepository;

    FriendshipRepositoryExtended friendshipRepository;

    public EventSubServiceExtended(
        EventSubRepositoryExtended eventSubRepository,
        EventSubMapper eventSubMapper,
        EventRepositoryExtended eventRepository,
        AppUserRepositoryExtended appUserRepository,
        FriendshipRepositoryExtended friendshipRepository
    ) {
        super(eventSubRepository, eventSubMapper);
        this.eventSubRepository = eventSubRepository;
        this.eventSubMapper = eventSubMapper;
        this.eventRepository = eventRepository;
        this.appUserRepository = appUserRepository;
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * Get all eventPlayers from the event by id.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     * @return the list of {@link EventPlayerDTO}.
     */
    public List<EventPlayerDTO> getAllEventPlayersByEventId(Long eventId, String userLogin) {
        log.debug("Request to getAllEventPlayers by eventId : {} for User : {}", eventId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if Event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // If the event is private and the user is not the owner
        if (event.getIsPrivate() && !appUser.equals(event.getOwner())) {
            // check if the user is an accepted subscriber
            if (!isAcceptedSubscriber(appUser, event)) {
                throw new ForbiddenException(
                    "Forbidden to get the private event with id : " +
                    eventId +
                    " because you are not the owner neither an accepted subscriber with login : " +
                    userLogin
                );
            }
        }

        return eventSubRepository.getAllEventPlayersByEvent(event);
    }

    /**
     * Returns true if the user is already subscribed to the event.
     *
     * @param eventId the event id.
     * @param userLogin the login of the user.
     * @return true if the user is already subscribed to the event, false otherwise.
     */
    public boolean isAlreadySubscribedByEventId(Long eventId, String userLogin) {
        log.debug("Request to isAlreadySubscribed by eventId : {} for User : {}", eventId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if Event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        return eventSubRepository.isAlreadySubscribed(appUser, event);
    }

    /**
     * Returns true if the user is subscribed and accepted to the event.
     *
     * @param appUser the AppUser.
     * @param event the Event
     * @return true if the user is subscribed and accepted to the event, false otherwise.
     */
    public boolean isAcceptedSubscriber(AppUser appUser, Event event) {
        log.debug("Request isAcceptedSubscriber with AppUser id : {} and Event id : {}", appUser.getId(), event.getId());

        return eventSubRepository.isAcceptedSubscriber(appUser, event);
    }

    /**
     * Returns true if the user is already accepted to the event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     * @return true if the user is already accepted to the event, false otherwise.
     */
    public boolean isAlreadyAcceptedByEventId(Long eventId, String userLogin) {
        log.debug("Request isAlreadyAccepted for User : {} by Event id : {}", userLogin, eventId);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if Event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        return eventSubRepository.isAcceptedSubscriber(appUser, event);
    }

    /**
     * Get all logged-in user's friends who can be invited to this event.
     * The owner of the event as to be the logged-in user.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     * @return the list of {@link EventFriendDTO}.
     */
    public List<EventFriendDTO> getAllFriendsForInviteByEventId(Long eventId, String userLogin) {
        log.debug("Request to getAllFriendsForInvite by eventId : {} for User : {}", eventId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if Event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // Check if the AppUser is the owner of the event
        if (!event.getOwner().equals(appUser)) {
            throw new ForbiddenException(
                "A user can only get friends for invite when he is owning the event" +
                " [UserLogin=" +
                userLogin +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        return eventSubRepository.getAllFriendsForInviteByEvent(appUser, event);
    }

    /**
     * Subscribes the user to the event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     * @return the {@link EventSubDTO} created.
     */
    public EventSubDTO subscribeUserByEventId(Long eventId, String userLogin) {
        log.debug("Request to subscribeUser by eventId : {} for User : {}", eventId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if Event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // Check if the AppUser is the owner of the Event
        if (appUser.equals(event.getOwner())) {
            throw new ForbiddenException(
                "A user cannot subscribes to his own event" + " [UserLogin=" + userLogin + ", EventId=" + eventId + "]"
            );
        }

        // Check if the Event is private
        if (event.getIsPrivate()) {
            throw new ForbiddenException(
                "A user cannot subscribes to private event" + " [UserLogin=" + userLogin + ", EventId=" + eventId + "]"
            );
        }

        // Check if the User is already subscribed
        if (eventSubRepository.isAlreadySubscribed(appUser, event)) {
            throw new ForbiddenException(
                "The user is already subscribed to the event" + " [UserLogin=" + userLogin + ", EventId=" + eventId + "]"
            );
        }

        // Construct the eventSub
        EventSub eventSub = new EventSub();
        eventSub.id(null).isAccepted(true).event(event).appUser(appUser);

        return eventSubMapper.toDto(eventSubRepository.save(eventSub));
    }

    /**
     * Unsubscribes the user from the event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     */
    public void unsubscribeUserByEventId(Long eventId, String userLogin) {
        log.debug("Request to unsubscribeUser by eventId : {} for User : {}", eventId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if Event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // Check if the User is already subscribed
        if (!eventSubRepository.isAlreadySubscribed(appUser, event)) {
            throw new ForbiddenException(
                "A user cannot unsubscribe from an event to which they are not yet subscribed" +
                " [UserLogin=" +
                userLogin +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        eventSubRepository.unsubscribeUserByEventId(appUser, event);
    }

    /**
     * Invites the user to an event owned by the logged-in User.
     *
     * @param eventId the id of the event.
     * @param appUserId the id of the appUser to invite.
     * @param userLogin the login of the user owning the event.
     * @return the created {@link EventSubDTO}.
     */
    public EventSubDTO inviteUserByEventIdAndAppUserId(Long eventId, Long appUserId, String userLogin) {
        log.debug("Request to inviteUser by eventId : {} and appUserId : {} from User : {}", eventId, appUserId, userLogin);

        // Check if the logged-in appUser exists
        AppUser appUserOwner = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the appUser to invite exists
        AppUser appUserToInvite = appUserRepository.findById(appUserId).orElseThrow(() -> new AppUserNotFoundException(appUserId));

        // Check if the event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // Check if the appUserOwner owned the event
        if (!event.getOwner().equals(appUserOwner)) {
            throw new ForbiddenException(
                "A user cannot invites friends to an event not owned by him" +
                " [UserLogin=" +
                appUserOwner.getInternalUser().getLogin() +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        // Check if the appUser to invites is a friend of the appUser owner.
        if (!friendshipRepository.existsFriendshipAcceptedBetweenAppUsers(appUserOwner, appUserToInvite)) {
            throw new ForbiddenException(
                "A user cannot be invited if he is not an accepted friend of the owner" +
                " [UserOwner=" +
                appUserOwner.getInternalUser().getLogin() +
                ", UserToInvite=" +
                appUserToInvite.getInternalUser().getLogin() +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        // Check if the appUser to invite is already subscribed
        if (eventSubRepository.isAlreadySubscribed(appUserToInvite, event)) {
            throw new ForbiddenException(
                "A user cannot be invited to an event to which they are already subscribed" +
                " [UserLogin=" +
                appUserToInvite.getInternalUser().getLogin() +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        // Create the new eventSub entity
        EventSub eventSub = new EventSub();
        eventSub.id(null).isAccepted(false).event(event).appUser(appUserToInvite);

        return eventSubMapper.toDto(eventSubRepository.save(eventSub));
    }

    /**
     * Accepts the invitation for the logged-in User to the Event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     * @return the updated {@link EventSubDTO}
     */
    public EventSubDTO acceptInviteByEventIdAndUserLogin(Long eventId, String userLogin) {
        log.debug("Request to acceptInvite by eventId : {} for User : {}", eventId, userLogin);

        // Check if the appUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // Check if the appUser received an invitation to the event
        EventSub eventSub = eventSubRepository
            .getEventSubByAppUserAndEvent(appUser, event)
            .orElseThrow(() ->
                new ForbiddenException(
                    "A user cannot accept invitation if he did not receive one" +
                    " [UserLogin=" +
                    appUser.getInternalUser().getLogin() +
                    ", EventId=" +
                    eventId +
                    "]"
                )
            );

        // Check if the appUser has already accepted the invitation
        if (eventSub.getIsAccepted()) {
            throw new ForbiddenException(
                "A user cannot accept an invitation which is already accepted" +
                " [UserLogin=" +
                appUser.getInternalUser().getLogin() +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        // Update the eventSub to accepted
        eventSub.setIsAccepted(true);

        return eventSubMapper.toDto(eventSubRepository.save(eventSub));
    }

    /**
     * Refuses the invitation for the logged-in User to the Event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     */
    public void refuseInviteByEventIdAndUserLogin(Long eventId, String userLogin) {
        log.debug("Request to refuseInvite by eventId : {} for User : {}", eventId, userLogin);

        // Check if the appUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // Check if the appUser received an invitation to the event
        EventSub eventSub = eventSubRepository
            .getEventSubByAppUserAndEvent(appUser, event)
            .orElseThrow(() ->
                new ForbiddenException(
                    "A user cannot refuse invitation if he did not receive one" +
                    " [UserLogin=" +
                    appUser.getInternalUser().getLogin() +
                    ", EventId=" +
                    eventId +
                    "]"
                )
            );

        // Check if the appUser has already accepted the invitation
        if (eventSub.getIsAccepted()) {
            throw new ForbiddenException(
                "A user cannot refuse an invitation which is already accepted" +
                " [UserLogin=" +
                appUser.getInternalUser().getLogin() +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        // Delete the refused eventSub
        eventSubRepository.delete(eventSub);
    }

    /**
     * Deletes the subscription of the appUser from the Event.
     * The logged-in user have to be the owner of the event.
     *
     * @param appUserId the id of the appUser to delete.
     * @param eventId the id of the event.
     * @param userLogin the login of the user owning the event.
     */
    public void deleteEventSubFromOwner(Long appUserId, Long eventId, String userLogin) {
        log.debug("Request to deleteEventSub from Owner : {} by eventId : {} and appUserId : {}", userLogin, eventId, appUserId);

        // Check if the logged-in appUser owning the event exists
        AppUser appUserOwner = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        // Check if the appUserOwner owned the event
        if (!event.getOwner().equals(appUserOwner)) {
            throw new ForbiddenException(
                "A user cannot delete players from an event not owned by him" +
                " [UserLogin=" +
                appUserOwner.getInternalUser().getLogin() +
                ", EventId=" +
                eventId +
                "]"
            );
        }

        // Check if the appUser to delete exists
        AppUser appUserToDelete = appUserRepository.findById(appUserId).orElseThrow(() -> new AppUserNotFoundException(appUserId));

        // Check if the appUser to delete is subscribed to the event (eventSub exist)
        EventSub eventSubToDelete = eventSubRepository
            .getEventSubByAppUserAndEvent(appUserToDelete, event)
            .orElseThrow(() -> new EventSubNotFoundException(appUserToDelete.getId(), event.getId()));

        // Delete the eventSub
        eventSubRepository.delete(eventSubToDelete);
    }
}
