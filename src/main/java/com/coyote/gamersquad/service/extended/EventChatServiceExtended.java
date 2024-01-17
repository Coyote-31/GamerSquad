package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.EventChat;
import com.coyote.gamersquad.domain.dto.projection.EventPlayerChatDTO;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventChatRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventSubRepositoryExtended;
import com.coyote.gamersquad.service.EventChatService;
import com.coyote.gamersquad.service.dto.EventChatDTO;
import com.coyote.gamersquad.service.dto.form.EventMessageDTO;
import com.coyote.gamersquad.service.errors.AppUserNotFoundException;
import com.coyote.gamersquad.service.errors.EventNotFoundException;
import com.coyote.gamersquad.service.errors.ForbiddenException;
import com.coyote.gamersquad.service.mapper.EventChatMapper;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link EventChat}.
 */
@Service
@Transactional
public class EventChatServiceExtended extends EventChatService {

    private final Logger log = LoggerFactory.getLogger(EventChatServiceExtended.class);

    EventChatRepositoryExtended eventChatRepository;

    EventChatMapper eventChatMapper;

    AppUserRepositoryExtended appUserRepository;

    EventRepositoryExtended eventRepository;

    EventSubRepositoryExtended eventSubRepository;

    public EventChatServiceExtended(
        EventChatRepositoryExtended eventChatRepository,
        EventChatMapper eventChatMapper,
        AppUserRepositoryExtended appUserRepository,
        EventRepositoryExtended eventRepository,
        EventSubRepositoryExtended eventSubRepository
    ) {
        super(eventChatRepository, eventChatMapper);
        this.eventChatRepository = eventChatRepository;
        this.eventChatMapper = eventChatMapper;
        this.appUserRepository = appUserRepository;
        this.eventRepository = eventRepository;
        this.eventSubRepository = eventSubRepository;
    }

    /**
     * Get all EventPlayerChats by eventId.
     * The logged-in User has to be the owner or an accepted user for this event.
     *
     * @param eventId the id of the event.
     * @param userLogin the login of user requesting.
     * @return list of {@link EventPlayerChatDTO}
     */
    public List<EventPlayerChatDTO> getAllEventPlayerChatsByEventId(Long eventId, String userLogin) {
        log.debug("Request to getAllEventPlayerChats with EventId : {} for User : {}", eventId, userLogin);

        // Check if the appUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        log.debug("--- event isPrivate : " + event.getIsPrivate() + " ---");

        boolean isAcceptedSub = eventSubRepository.isAcceptedSubscriber(appUser, event);
        log.debug("--- appUser isAcceptedSub : " + isAcceptedSub + " ---");

        // Check if the appUser is the owner or an accepted user of this event
        if (!event.getOwner().equals(appUser) && !isAcceptedSub) {
            throw new ForbiddenException(
                "Forbidden to get eventPlayerChats because appUser with id : " +
                appUser.getId() +
                " is not the owner or an accepted user of the event with id : " +
                event.getId()
            );
        }

        return eventChatRepository.getAllEventPlayerChatsByEvent(event);
    }

    /**
     * Save a new EventChat with a message from the UserLogin with the eventId.
     *
     * @param eventMessage the message of the new EventChat.
     * @param eventId the id of the event.
     * @param userLogin the login of the User.
     * @return the {@link EventChatDTO} created.
     */
    public EventChatDTO createEventChatMessage(EventMessageDTO eventMessage, Long eventId, String userLogin) {
        log.debug("Request to save EventChat message with eventId : {} for User : {}", eventId, userLogin);

        // Check if the appUser exists
        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the event exists
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        log.debug("--- event isPrivate : " + event.getIsPrivate() + " ---");

        boolean isAcceptedSub = eventSubRepository.isAcceptedSubscriber(appUser, event);
        log.debug("--- appUser isAcceptedSub : " + isAcceptedSub + " ---");

        // Check if the appUser is the owner or an accepted user of this event
        if (!event.getOwner().equals(appUser) && !isAcceptedSub) {
            throw new ForbiddenException(
                "Forbidden to create eventChat because appUser with id : " +
                appUser.getId() +
                " is not the owner or an accepted user of the event with id : " +
                event.getId()
            );
        }

        // Create the new eventChat to save
        EventChat eventChatToSave = new EventChat();
        eventChatToSave.message(eventMessage.getMessage()).sendAt(Instant.now()).event(event).appUser(appUser);

        return eventChatMapper.toDto(eventChatRepository.save(eventChatToSave));
    }
}
