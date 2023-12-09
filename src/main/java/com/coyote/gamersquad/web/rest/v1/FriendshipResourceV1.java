package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.projection.PlayerFriendshipDTO;
import com.coyote.gamersquad.service.extended.FriendshipServiceExtended;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
