package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.domain.dto.projection.EventPlayerChatDTO;
import com.coyote.gamersquad.service.dto.EventChatDTO;
import com.coyote.gamersquad.service.dto.form.EventMessageDTO;
import com.coyote.gamersquad.service.extended.EventChatServiceExtended;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.EventChat}.
 */
@RestController
@RequestMapping("/api/v1")
public class EventChatResourceV1 {

    private final Logger log = LoggerFactory.getLogger(EventChatResourceV1.class);

    private static final String ENTITY_NAME = "eventChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventChatServiceExtended eventChatService;

    public EventChatResourceV1(EventChatServiceExtended eventChatService) {
        this.eventChatService = eventChatService;
    }

    /**
     * {@code GET  /event-chats/event/:eventId} : Get all EventPlayerChats by eventId.
     * Only send to everyone when the event is public.
     * If the event is private, the logged-in User has to be the owner or an accepted user for this event.
     *
     * @param eventId the id of the event to retrieve EventChats from.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link EventPlayerChatDTO} in body.
     */
    @GetMapping("/event-chats/event/{eventId}")
    public ResponseEntity<List<EventPlayerChatDTO>> getAllEventPlayerChatsByEventId(
        @PathVariable(value = "eventId") Long eventId,
        HttpServletRequest request
    ) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to getAllEventPlayerChats with EventId : {} for User : {}", eventId, userLogin);

        List<EventPlayerChatDTO> result = eventChatService.getAllEventPlayerChatsByEventId(eventId, userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /event-chats/event/:eventId} : Create a new EventChat by eventId
     * with the message in the body.
     *
     * @param eventMessage the message to create EventChat.
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventChatDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-chats/event/{eventId}")
    public ResponseEntity<EventChatDTO> createEventChatMessage(
        @Valid @RequestBody EventMessageDTO eventMessage,
        @PathVariable(value = "eventId") Long eventId,
        HttpServletRequest request
    ) throws URISyntaxException {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to save EventChat message with eventId : {} for User : {}", eventId, userLogin);

        EventChatDTO result = eventChatService.createEventChatMessage(eventMessage, eventId, userLogin);

        return ResponseEntity.created(new URI("/api/event-chats/" + result.getId())).body(result);
    }
}
