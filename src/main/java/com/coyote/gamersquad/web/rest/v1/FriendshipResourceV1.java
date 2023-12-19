package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
import com.coyote.gamersquad.service.dto.projection.PlayerFriendshipDTO;
import com.coyote.gamersquad.service.extended.FriendshipServiceExtended;
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
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.Friendship}.
 */
@RestController
@RequestMapping("/api/v1")
public class FriendshipResourceV1 {

    private final Logger log = LoggerFactory.getLogger(FriendshipResourceV1.class);

    private static final String ENTITY_NAME = "friendship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendshipServiceExtended friendshipService;

    public FriendshipResourceV1(FriendshipServiceExtended friendshipService) {
        this.friendshipService = friendshipService;
    }

    /**
     * {@code GET  /friendships/app-users} : Get all AppUsers friends with the logged-in User.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link AppUserDTO} in body.
     */
    @GetMapping("/friendships/app-users")
    public ResponseEntity<List<AppUserDTO>> getAllMyFriends(HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST Request to getAllMyFriends for User : {}", userLogin);

        List<AppUserDTO> result = friendshipService.getAllFriends(userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /friendships/players} : Get all Players friends with the logged-in User.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link PlayerFriendshipDTO} in body.
     */
    @GetMapping("/friendships/players")
    public ResponseEntity<List<PlayerFriendshipDTO>> getAllMyPlayersFriends(HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST Request to getAllMyPlayersFriends for User : {}", userLogin);

        List<PlayerFriendshipDTO> result = friendshipService.getAllPlayersFriends(userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /friendships/:friendshipId/player} : Get the Player friend with the logged-in User by friendshipId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the {@link PlayerFriendshipDTO} in body.
     */
    @GetMapping("/friendships/{friendshipId}/player")
    public ResponseEntity<PlayerFriendshipDTO> getMyPlayerFriendByFriendshipId(
        @PathVariable(value = "friendshipId") Long friendshipId,
        HttpServletRequest request
    ) {
        String userLogin = request.getRemoteUser();

        log.debug("REST Request to getMyPlayerFriend with Friendship Id : {} for User : {}", friendshipId, userLogin);

        PlayerFriendshipDTO result = friendshipService.getPlayerFriendByFriendshipId(friendshipId, userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /friendships/app-user/:appUserId/demand} : Create friendship demand to the AppUser with the logged-in User.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (CREATED)} and the created {@link FriendshipDTO} in body.
     */
    @PostMapping("/friendships/app-user/{appUserId}/demand")
    public ResponseEntity<FriendshipDTO> createFriendshipDemand(
        @PathVariable(value = "appUserId") Long appUserId,
        HttpServletRequest request
    ) throws URISyntaxException {
        String userLogin = request.getRemoteUser();

        log.debug("REST Request to createFriendshipDemand to AppUser : {} for User : {}", appUserId, userLogin);

        FriendshipDTO result = friendshipService.createFriendshipDemand(appUserId, userLogin);

        return ResponseEntity
            .created(new URI("/api/friendships/" + result.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "Demande d'ami envoyée", ""))
            .body(result);
    }

    /**
     * {@code PATCH  /friendships/app-user/:appUserId/accept} : Accept the friendship demand from the AppUser with the logged-in User.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the updated {@link FriendshipDTO} in body.
     */
    @PatchMapping("/friendships/app-user/{appUserId}/accept")
    public ResponseEntity<FriendshipDTO> acceptFriendshipDemand(
        @PathVariable(value = "appUserId") Long appUserId,
        HttpServletRequest request
    ) {
        String userLogin = request.getRemoteUser();

        log.debug("REST Request to acceptFriendshipDemand from AppUser : {} for User : {}", appUserId, userLogin);

        FriendshipDTO result = friendshipService.acceptFriendshipDemand(appUserId, userLogin);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "Demande d'ami acceptée", "")).body(result);
    }

    /**
     * {@code DELETE  /friendships/app-user/:appUserId/delete} : Delete the friendship between the AppUser and the logged-in User.
     *
     * @return the {@link ResponseEntity} with status {@code 204 (NO CONTENT)}.
     */
    @DeleteMapping("/friendships/app-user/{appUserId}/delete")
    public ResponseEntity<Void> deleteFriendship(@PathVariable(value = "appUserId") Long appUserId, HttpServletRequest request) {
        String userLogin = request.getRemoteUser();

        log.debug("REST Request to deleteFriendship between AppUser : {} and User : {}", appUserId, userLogin);

        friendshipService.deleteFriendship(appUserId, userLogin);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "Ami(e) supprimé(e)", "")).build();
    }
}
