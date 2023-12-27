package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.projection.EventPlayerDTO;
import com.coyote.gamersquad.service.extended.EventSubServiceExtended;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.EventSub}.
 */
@RestController
@RequestMapping("/api/v1")
public class EventSubResourceV1 {

    private final Logger log = LoggerFactory.getLogger(EventSubResourceV1.class);

    private static final String ENTITY_NAME = "eventSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventSubServiceExtended eventSubService;

    public EventSubResourceV1(EventSubServiceExtended eventSubService) {
        this.eventSubService = eventSubService;
    }

    /**
     * {@code GET  /event-subs/:eventId/event-players} : Get all EventPlayers by eventId.
     * If the event is private checks if the logged-in user is owner or accepted.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link EventPlayerDTO} in body.
     */
    @GetMapping("/event-subs/{eventId}/event-players")
    public ResponseEntity<List<EventPlayerDTO>> getAllEventPlayersByEventId(
        @PathVariable(value = "eventId") Long eventId,
        HttpServletRequest request
    ) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to getAllEventPlayers by eventId : {} for User : {}", eventId, userLogin);

        List<EventPlayerDTO> result = eventSubService.getAllEventPlayersByEventId(eventId, userLogin);

        return ResponseEntity.ok().body(result);
    }
}
