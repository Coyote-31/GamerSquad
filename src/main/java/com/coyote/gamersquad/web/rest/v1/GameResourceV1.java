package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.dto.GameDTO;
import com.coyote.gamersquad.service.extended.GameServiceExtended;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.Game}.
 */
@RestController
@RequestMapping("/api/v1")
public class GameResourceV1 {

    private final Logger log = LoggerFactory.getLogger(GameResourceV1.class);

    private static final String ENTITY_NAME = "game";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameServiceExtended gameService;

    public GameResourceV1(GameServiceExtended gameService) {
        this.gameService = gameService;
    }

    /**
     * {@code GET  /games} : get all the games.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of games in body.
     */
    @GetMapping("/games")
    public List<GameDTO> getAllGames() {
        log.debug("Api v1 REST request to get all Games");
        return gameService.findAll();
    }
}
