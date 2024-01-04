package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.repository.extended.*;
import com.coyote.gamersquad.service.EventService;
import com.coyote.gamersquad.service.dto.form.EventCreateDTO;
import com.coyote.gamersquad.service.dto.projection.EventDetailDTO;
import com.coyote.gamersquad.service.mapper.EventMapper;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link Event}.
 */
@Service
@Transactional
public class EventServiceExtended extends EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceExtended.class);

    EventRepositoryExtended eventRepository;

    EventSubServiceExtended eventSubService;

    EventSubRepositoryExtended eventSubRepository;

    EventChatRepositoryExtended eventChatRepository;

    AppUserRepositoryExtended appUserRepository;

    GameRepositoryExtended gameRepository;

    public EventServiceExtended(
        EventRepositoryExtended eventRepository,
        EventMapper eventMapper,
        EventSubServiceExtended eventSubService,
        EventSubRepositoryExtended eventSubRepository,
        EventChatRepositoryExtended eventChatRepository,
        AppUserRepositoryExtended appUserRepository,
        GameRepositoryExtended gameRepository
    ) {
        super(eventRepository, eventMapper);
        this.eventRepository = eventRepository;
        this.eventSubService = eventSubService;
        this.eventSubRepository = eventSubRepository;
        this.eventChatRepository = eventChatRepository;
        this.appUserRepository = appUserRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Get all EventDetails public by Game id.
     * Only where meeting date is after now.
     *
     * @param gameId the game id.
     * @return the list of {@link EventDetailDTO}.
     */
    public List<EventDetailDTO> getAllEventDetailsPublicByGameId(Long gameId) {
        log.debug("Request to get all EventDetails public by Game id : {}", gameId);

        // Check if a Game exists with this id
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found with ID : " + gameId));

        return eventRepository.getAllEventDetailsPublicByGameId(game);
    }

    /**
     * Get EventDetail by Event id.
     * If the event is private check if the user is owner or an accepted subscriber of the event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user.
     * @return the {@link EventDetailDTO}.
     */
    public EventDetailDTO getEventDetailByEventId(Long eventId, String userLogin) {
        log.debug("Request to get EventDetail by Event id : {} for User : {}", eventId, userLogin);

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
            if (!eventSubService.isAcceptedSubscriber(appUser, event)) {
                throw new AccessDeniedException(
                    "Access denied to the event with id : " +
                    eventId +
                    " because you are not the owner neither an accepted subscriber with login : " +
                    userLogin
                );
            }
        }

        return eventRepository.getEventDetailByEventId(eventId);
    }

    /**
     * Get all EventDetails owned by User Login.
     *
     * @param userLogin the login of the user.
     * @return the list of {@link EventDetailDTO}.
     */
    public List<EventDetailDTO> getAllEventDetailsOwnedByUserLogin(String userLogin) {
        log.debug("Request to get all EventDetails owned by User : {}", userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        return eventRepository.getAllEventDetailsOwnedByAppUser(appUser);
    }

    /**
     * Get all EventDetails subscribed by User Login.
     *
     * @param userLogin the login of the user.
     * @return the list of {@link EventDetailDTO}.
     */
    public List<EventDetailDTO> getAllEventDetailsSubscribedByUserLogin(String userLogin) {
        log.debug("Request to get all EventDetails subscribed by User : {}", userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        return eventRepository.getAllEventDetailsSubscribedByAppUser(appUser);
    }

    /**
     * Get all EventDetails pending by User Login.
     *
     * @param userLogin the login of the user.
     * @return the list of {@link EventDetailDTO}.
     */
    public List<EventDetailDTO> getAllEventDetailsPendingByUserLogin(String userLogin) {
        log.debug("Request to get all EventDetails pending by User : {}", userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        return eventRepository.getAllEventDetailsPendingByAppUser(appUser);
    }

    /**
     * Creates an Event from EventCreate form for a Game with User as owner.
     *
     * @param eventForm the form of the new event.
     * @param gameId the game id.
     * @param userLogin the user login.
     * @return the persisted Event as {@link EventDetailDTO}.
     */
    public EventDetailDTO createEvent(EventCreateDTO eventForm, Long gameId, String userLogin) {
        log.debug("Request to create a new Event for Game id : {} with User : {}", gameId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if the Game exists
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found with id : " + gameId));

        // Create the new entity
        Event eventEntity = new Event()
            .id(null)
            .title(eventForm.getTitle())
            .description(eventForm.getDescription())
            .meetingDate(eventForm.getMeetingDate())
            .isPrivate(eventForm.getIsPrivate())
            .owner(appUser)
            .game(game);

        // Save the entity and return the id
        Long eventId = eventRepository.save(eventEntity).getId();

        return eventRepository.getEventDetailByEventId(eventId);
    }

    /**
     * Updates the Event by id with the User logged-in as owner.
     *
     * @param eventForm the form to update the event from.
     * @param eventId the id of the event.
     * @param userLogin the login of the user owner.
     * @return the updated {@link EventDetailDTO}.
     */
    public EventDetailDTO updateEvent(EventCreateDTO eventForm, Long eventId, String userLogin) {
        log.debug("Request to update the Event by id : {} with owner User : {}", eventId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if Event exists
        Event event = eventRepository
            .findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found with id : " + eventId));

        // Check if the AppUser is the owner
        if (!event.getOwner().equals(appUser)) {
            throw new AccessDeniedException(
                "Access denied to update event with id : " + eventId + " because you are not the owner with login : " + userLogin
            );
        }

        // Create the updated entity
        Event eventUpdated = new Event()
            .id(eventId)
            .title(eventForm.getTitle())
            .description(eventForm.getDescription())
            .meetingDate(eventForm.getMeetingDate())
            .isPrivate(eventForm.getIsPrivate())
            .owner(appUser)
            .game(event.getGame());

        // Update the entity
        eventRepository.save(eventUpdated);

        return eventRepository.getEventDetailByEventId(eventId);
    }

    /**
     * Deletes the event by id.
     * The logged-in user have to be the owner of the event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of the user owner.
     */
    public void deleteEventByIdFromOwner(Long eventId, String userLogin) {
        log.debug("Request to deleteEvent by eventId : {} from Owner : {}", eventId, userLogin);

        // Check if AppUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if Event exists
        Event event = eventRepository
            .findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("Event not found with id : " + eventId));

        // Check if the AppUser is the owner
        if (!event.getOwner().equals(appUser)) {
            throw new AccessDeniedException(
                "Access denied to delete event with id : " + eventId + " because you are not the owner with login : " + userLogin
            );
        }

        // Delete EventChats
        eventChatRepository.deleteAllByEvent(event);

        // Delete EventSubs
        eventSubRepository.deleteAllByEvent(event);

        // Delete Event
        eventRepository.delete(event);
    }
}
