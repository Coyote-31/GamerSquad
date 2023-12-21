package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.projection.EventDetailDTO;
import com.coyote.gamersquad.service.extended.EventServiceExtended;
import java.util.List;
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
    public List<EventDetailDTO> getAllEventDetailsPublicByGameId(@PathVariable("gameId") Long gameId) {
        log.debug("REST request to get all EventDetails public by Game id : {}", gameId);

        return eventService.getAllEventDetailsPublicByGameId(gameId);
    }
}
