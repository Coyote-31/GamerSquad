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
public class FriendshipResource {

    private final Logger log = LoggerFactory.getLogger(FriendshipResource.class);

    private static final String ENTITY_NAME = "friendship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendshipServiceExtended friendshipService;

    public FriendshipResource(FriendshipServiceExtended friendshipService) {
        this.friendshipService = friendshipService;
    }
}
