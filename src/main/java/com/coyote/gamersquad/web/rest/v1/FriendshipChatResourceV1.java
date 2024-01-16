package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.domain.dto.projection.PlayerChatDTO;
import com.coyote.gamersquad.service.dto.FriendshipChatDTO;
import com.coyote.gamersquad.service.dto.form.FriendMessageDTO;
import com.coyote.gamersquad.service.extended.FriendshipChatServiceExtended;
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
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.FriendshipChat}.
 */
@RestController
@RequestMapping("/api/v1")
public class FriendshipChatResourceV1 {

    private final Logger log = LoggerFactory.getLogger(FriendshipChatResourceV1.class);

    private static final String ENTITY_NAME = "friendshipChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendshipChatServiceExtended friendshipChatService;

    public FriendshipChatResourceV1(FriendshipChatServiceExtended friendshipChatService) {
        this.friendshipChatService = friendshipChatService;
    }

    /**
     * {@code GET  /friendship-chats/friendship/:friendshipId} : Get all PlayerChats by friendshipId
     * related with the logged-in User.
     *
     * @param friendshipId the id of the friendship to retrieve PlayerChats from.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link PlayerChatDTO} in body.
     */
    @GetMapping("/friendship-chats/friendship/{friendshipId}")
    public ResponseEntity<List<PlayerChatDTO>> getAllPlayerChatsByFriendshipId(
        @PathVariable(value = "friendshipId") Long friendshipId,
        HttpServletRequest request
    ) {
        String userLogin = request.getRemoteUser();

        log.debug("REST Request to getAllPlayerChats with FriendshipId : {} for User : {}", friendshipId, userLogin);

        List<PlayerChatDTO> result = friendshipChatService.getAllPlayerChatsByFriendshipId(friendshipId, userLogin);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /friendship-chats/friendship/:friendshipId} : Create a new FriendshipChat by friendshipId
     * with the message in the body.
     *
     * @param friendMessage the message to create FriendshipChat.
     * @param friendshipId the id of the friendship.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendshipChatDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friendship-chats/friendship/{friendshipId}")
    public ResponseEntity<FriendshipChatDTO> createFriendshipChatMessage(
        @Valid @RequestBody FriendMessageDTO friendMessage,
        @PathVariable(value = "friendshipId") Long friendshipId,
        HttpServletRequest request
    ) throws URISyntaxException {
        String userLogin = request.getRemoteUser();

        log.debug("REST request to save FriendshipChat message with friendshipId : {} for User : {}", friendshipId, userLogin);

        FriendshipChatDTO result = friendshipChatService.createFriendshipChatMessage(friendMessage, friendshipId, userLogin);

        return ResponseEntity.created(new URI("/api/friendship-chats/" + result.getId())).body(result);
    }
}
