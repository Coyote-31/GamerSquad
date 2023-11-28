package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.GameServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.Game}.
 */
@RestController
@RequestMapping("/api/v1")
public class GameResource {

    private final Logger log = LoggerFactory.getLogger(GameResource.class);

    private static final String ENTITY_NAME = "game";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameServiceExtended gameService;

    public GameResource(GameServiceExtended gameService) {
        this.gameService = gameService;
    }
}
