package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.GameSubServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.GameSub}.
 */
@RestController
@RequestMapping("/api/v1")
public class GameSubResource {

    private final Logger log = LoggerFactory.getLogger(GameSubResource.class);

    private static final String ENTITY_NAME = "gameSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameSubServiceExtended gameSubService;

    public GameSubResource(GameSubServiceExtended gameSubService) {
        this.gameSubService = gameSubService;
    }
}
