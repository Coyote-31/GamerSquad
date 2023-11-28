package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.FriendshipChatServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
