package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.form.EventCreateDTO;
import com.coyote.gamersquad.service.dto.projection.EventDetailDTO;
import com.coyote.gamersquad.service.extended.EventServiceExtended;
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
     * {@code GET  /games/:gameId/event-details} : Get all the EventDetails public by gameId.
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
     * {@code GET  /events/:eventId/event-detail} : Get the EventDetail by Event id.
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

    /**
     * {@code GET  /events/my-events/owned} : Get all EventDetails owned by the logged-in User.
     *
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link EventDetailDTO} in body.
     */
    @GetMapping("/events/my-events/owned")
    public ResponseEntity<List<EventDetailDTO>> getAllEventDetailsOwnedByUserLoggedIn(HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to get all EventDetails owned by User : {}", userLogin);

        List<EventDetailDTO> result = eventService.getAllEventDetailsOwnedByUserLogin(userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /events/my-events/subscribed} : Get all EventDetails subscribed by the logged-in User.
     *
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link EventDetailDTO} in body.
     */
    @GetMapping("/events/my-events/subscribed")
    public ResponseEntity<List<EventDetailDTO>> getAllEventDetailsSubscribedByUserLoggedIn(HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to get all EventDetails subscribed by User : {}", userLogin);

        List<EventDetailDTO> result = eventService.getAllEventDetailsSubscribedByUserLogin(userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /events/my-events/pending} : Get all EventDetails pending by the logged-in User.
     *
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link EventDetailDTO} in body.
     */
    @GetMapping("/events/my-events/pending")
    public ResponseEntity<List<EventDetailDTO>> getAllEventDetailsPendingByUserLoggedIn(HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to get all EventDetails pending by User : {}", userLogin);

        List<EventDetailDTO> result = eventService.getAllEventDetailsPendingByUserLogin(userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST /events/game/:gameId/create} : Creates a new Event for a Game with the User logged-in as owner.
     *
     * @param eventForm the form to create the event from.
     * @param gameId the id of the game.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and the new {@code EventDetailDTO} in the body.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/events/game/{gameId}/create")
    public ResponseEntity<EventDetailDTO> createEvent(
        @Valid @RequestBody EventCreateDTO eventForm,
        @PathVariable("gameId") Long gameId,
        HttpServletRequest request
    ) throws URISyntaxException {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to create a new Event for Game id : {} with User : {}", gameId, userLogin);

        EventDetailDTO result = eventService.createEvent(eventForm, gameId, userLogin);

        return ResponseEntity.created(new URI("/api/events/" + result.getId())).body(result);
    }
}
