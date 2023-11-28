package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.FriendshipServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
}
