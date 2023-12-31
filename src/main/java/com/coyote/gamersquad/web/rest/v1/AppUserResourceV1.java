package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.AppUserServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.AppUser}.
 */
@RestController
@RequestMapping("/api/v1")
public class AppUserResourceV1 {

    private final Logger log = LoggerFactory.getLogger(AppUserResourceV1.class);

    private static final String ENTITY_NAME = "appUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppUserServiceExtended appUserService;

    public AppUserResourceV1(AppUserServiceExtended appUserService) {
        this.appUserService = appUserService;
    }
}
