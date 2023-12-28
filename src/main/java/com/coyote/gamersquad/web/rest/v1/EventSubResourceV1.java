package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.EventSubDTO;
import com.coyote.gamersquad.service.dto.projection.EventFriendDTO;
import com.coyote.gamersquad.service.dto.projection.EventPlayerDTO;
import com.coyote.gamersquad.service.extended.EventSubServiceExtended;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

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
     * {@code GET  /event-subs/event/:eventId/event-players} : Get all EventPlayers by eventId.
     * If the event is private checks if the logged-in user is owner or accepted.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link EventPlayerDTO} in body.
     */
    @GetMapping("/event-subs/event/{eventId}/event-players")
    public ResponseEntity<List<EventPlayerDTO>> getAllEventPlayersByEventId(
        @PathVariable(value = "eventId") Long eventId,
        HttpServletRequest request
    ) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to getAllEventPlayers by eventId : {} for User : {}", eventId, userLogin);

        List<EventPlayerDTO> result = eventSubService.getAllEventPlayersByEventId(eventId, userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /event-subs/event/:eventId/is-subscribed} : Is the logged-in user subscribed to the event.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return If the user is already subscribed to this event as {@link Boolean} in the body.
     */
    @GetMapping("/event-subs/event/{eventId}/is-subscribed")
    public ResponseEntity<Boolean> isAlreadySubscribedByEventId(@PathVariable(value = "eventId") Long eventId, HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to isAlreadySubscribed by eventId : {} for User : {}", eventId, userLogin);

        boolean result = eventSubService.isAlreadySubscribedByEventId(eventId, userLogin);

        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET /event-subs/event/:eventId/event-friends} : Get all logged-in user's friends who can be invited to this event.
     * The owner of the event as to be the logged-in user.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (Ok)} and the list of {@link EventFriendDTO} in body.
     */
    @GetMapping("/event-subs/event/{eventId}/event-friends")
    public ResponseEntity<List<EventFriendDTO>> getAllFriendsForInviteByEventId(
        @PathVariable(value = "eventId") Long eventId,
        HttpServletRequest request
    ) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to getAllFriendsForInvite by eventId : {} for User : {}", eventId, userLogin);

        List<EventFriendDTO> result = eventSubService.getAllFriendsForInviteByEventId(eventId, userLogin);

        return ResponseEntity.ok(result);
    }

    /**
     * {@code POST  /event-subs/event/:eventId/subscribe} : Subscribes the logged-in User to the Event.
     * The Event should not be private.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 201 (CREATED)} and the {@link EventSubDTO} in body.
     */
    @PostMapping("/event-subs/event/{eventId}/subscribe")
    public ResponseEntity<EventSubDTO> subscribeUserByEventId(@PathVariable(value = "eventId") Long eventId, HttpServletRequest request)
        throws URISyntaxException {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to subscribeUser by eventId : {} for User : {}", eventId, userLogin);

        EventSubDTO result = eventSubService.subscribeUserByEventId(eventId, userLogin);

        return ResponseEntity
            .created(new URI("/api/event-subs/" + result.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "Vous êtes maintenant inscrit à l'évènement", ""))
            .body(result);
    }

    /**
     * {@code DELETE  /event-subs/event/:eventId/unsubscribe} : Unsubscribes the logged-in User from the Event.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the empty body with status {@code 200 (Ok)}.
     */
    @DeleteMapping("/event-subs/event/{eventId}/unsubscribe")
    public ResponseEntity<Void> unsubscribeUserByEventId(@PathVariable(value = "eventId") Long eventId, HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to unsubscribeUser by eventId : {} for User : {}", eventId, userLogin);

        eventSubService.unsubscribeUserByEventId(eventId, userLogin);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "Vous n'êtes plus inscrit à l'évènement", "")).build();
    }

    /**
     * {@code POST  /event-subs/event/:eventId/app-user/:appUserId/invite} : Invites the user to an event owned by the logged-in User.
     *
     * @param eventId the id of the event.
     * @param appUserId the id of the appUser.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 201 (CREATED)} and the {@link EventSubDTO} in body.
     */
    @PostMapping("/event-subs/event/{eventId}/app-user/{appUserId}/invite")
    public ResponseEntity<EventSubDTO> inviteUserByEventIdAndAppUserId(
        @PathVariable(value = "eventId") Long eventId,
        @PathVariable(value = "appUserId") Long appUserId,
        HttpServletRequest request
    ) throws URISyntaxException {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to inviteUser by eventId : {} and appUserId : {} from User : {}", eventId, appUserId, userLogin);

        EventSubDTO result = eventSubService.inviteUserByEventIdAndAppUserId(eventId, appUserId, userLogin);

        return ResponseEntity
            .created(new URI("/api/event-subs/" + result.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "Invitation à l'évènement envoyée", ""))
            .body(result);
    }

    /**
     * {@code PATCH  /event-subs/event/:eventId/accept-invite} : Accepts the invitation for the logged-in User to the Event.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (Ok)} and the updated {@link EventSubDTO} in body.
     */
    @PatchMapping("/event-subs/event/{eventId}/accept-invite")
    public ResponseEntity<EventSubDTO> acceptInviteByEventId(@PathVariable(value = "eventId") Long eventId, HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to acceptInvite by eventId : {} for User : {}", eventId, userLogin);

        EventSubDTO result = eventSubService.acceptInviteByEventIdAndUserLogin(eventId, userLogin);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "Invitation à l'évènement acceptée", "")).body(result);
    }

    /**
     * {@code DELETE  /event-subs/event/:eventId/refuse-invite} : Refuses the invitation for the logged-in User to the Event.
     *
     * @param eventId the id of the event.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-subs/event/{eventId}/refuse-invite")
    public ResponseEntity<Void> refuseInviteByEventId(@PathVariable(value = "eventId") Long eventId, HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to refuseInvite by eventId : {} for User : {}", eventId, userLogin);

        eventSubService.refuseInviteByEventIdAndUserLogin(eventId, userLogin);

        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "Invitation à l'évènement refusée", "")).build();
    }
}
