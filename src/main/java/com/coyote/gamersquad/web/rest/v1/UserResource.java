package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.UserServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.User}.
 */
@RestController
@RequestMapping("/api/v1")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserServiceExtended userService;

    public UserResource(UserServiceExtended userService) {
        this.userService = userService;
    }
}
