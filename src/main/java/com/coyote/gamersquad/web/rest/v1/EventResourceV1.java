package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.projection.EventDetailDTO;
import com.coyote.gamersquad.service.extended.EventServiceExtended;
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
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.Event}.
 */
@RestController
@RequestMapping("/api/v1")
public class EventResourceV1 {

    private final Logger log = LoggerFactory.getLogger(EventResourceV1.class);

    private static final String ENTITY_NAME = "event";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventServiceExtended eventService;

    public EventResourceV1(EventServiceExtended eventService) {
        this.eventService = eventService;
    }

    /**
     * {@code GET  /games/:gameId/event-details} : get all the EventDetails public by gameId.
     * Only where meeting date is after now.
     *
     * @param gameId the id of the game.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link EventDetailDTO} in body.
     */
    @GetMapping("/games/{gameId}/event-details")
    public ResponseEntity<List<EventDetailDTO>> getAllEventDetailsPublicByGameId(@PathVariable("gameId") Long gameId) {
        log.debug("REST request to get all EventDetails public by Game id : {}", gameId);

        List<EventDetailDTO> result = eventService.getAllEventDetailsPublicByGameId(gameId);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /events/:eventId/event-detail} : get the EventDetail by Event id.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the {@link EventDetailDTO} in body.
     */
    @GetMapping("/events/{eventId}/event-detail")
    public ResponseEntity<EventDetailDTO> getEventDetailByEventId(@PathVariable("eventId") Long eventId, HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to get EventDetail by Event id : {} for User : {}", eventId, userLogin);

        EventDetailDTO result = eventService.getEventDetailByEventId(eventId, userLogin);

        return ResponseEntity.ok().body(result);
    }
}
