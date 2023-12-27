package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.EventSub;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventSubRepositoryExtended;
import com.coyote.gamersquad.service.EventSubService;
import com.coyote.gamersquad.service.dto.EventSubDTO;
import com.coyote.gamersquad.service.dto.projection.EventPlayerDTO;
import com.coyote.gamersquad.service.mapper.EventSubMapper;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
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

    public EventSubServiceExtended(
        EventSubRepositoryExtended eventSubRepository,
        EventSubMapper eventSubMapper,
        EventRepositoryExtended eventRepository,
        AppUserRepositoryExtended appUserRepository
    ) {
        super(eventSubRepository, eventSubMapper);
        this.eventSubRepository = eventSubRepository;
        this.eventSubMapper = eventSubMapper;
        this.eventRepository = eventRepository;
        this.appUserRepository = appUserRepository;
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
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if Event exists
        Event event = eventRepository
            .findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found with id : " + eventId));

        // If the event is private and the user is not the owner
        if (event.getIsPrivate() && !appUser.equals(event.getOwner())) {
            // check if the user is an accepted subscriber
            if (!isAcceptedSubscriber(appUser, event)) {
                throw new AccessDeniedException(
                    "Access denied to the private event with id : " +
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
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if Event exists
        Event event = eventRepository
            .findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found with id : " + eventId));

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
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if Event exists
        Event event = eventRepository
            .findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found with id : " + eventId));

        // Check if the AppUser is the owner of the Event
        if (appUser.equals(event.getOwner())) {
            throw new AccessDeniedException(
                "A user cannot subscribes to his own event" + " [UserLogin=" + userLogin + ", EventId=" + eventId + "]"
            );
        }

        // Check if the Event is private
        if (event.getIsPrivate()) {
            throw new AccessDeniedException(
                "A user cannot subscribes to private event" + " [UserLogin=" + userLogin + ", EventId=" + eventId + "]"
            );
        }

        // Check if the User is already subscribed
        if (eventSubRepository.isAlreadySubscribed(appUser, event)) {
            throw new AccessDeniedException(
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
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if Event exists
        Event event = eventRepository
            .findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found with id : " + eventId));

        // Check if the User is already subscribed
        if (!eventSubRepository.isAlreadySubscribed(appUser, event)) {
            throw new AccessDeniedException(
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
}
