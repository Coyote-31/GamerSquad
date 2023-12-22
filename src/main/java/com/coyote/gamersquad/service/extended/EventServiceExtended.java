package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventRepositoryExtended;
import com.coyote.gamersquad.repository.extended.GameRepositoryExtended;
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

    AppUserRepositoryExtended appUserRepository;

    GameRepositoryExtended gameRepository;

    public EventServiceExtended(
        EventRepositoryExtended eventRepository,
        EventMapper eventMapper,
        EventSubServiceExtended eventSubService,
        AppUserRepositoryExtended appUserRepository,
        GameRepositoryExtended gameRepository
    ) {
        super(eventRepository, eventMapper);
        this.eventRepository = eventRepository;
        this.eventSubService = eventSubService;
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
}
