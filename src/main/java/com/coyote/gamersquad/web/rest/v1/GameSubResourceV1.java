package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.repository.extended.GameRepositoryExtended;
import com.coyote.gamersquad.service.dto.GameSubDTO;
import com.coyote.gamersquad.service.extended.GameSubServiceExtended;
import com.coyote.gamersquad.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.GameSub}.
 */
@RestController
@RequestMapping("/api/v1")
public class GameSubResourceV1 {

    private final Logger log = LoggerFactory.getLogger(GameSubResourceV1.class);

    private static final String ENTITY_NAME = "gameSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameSubServiceExtended gameSubService;

    private final GameRepositoryExtended gameRepository;

    public GameSubResourceV1(GameSubServiceExtended gameSubService, GameRepositoryExtended gameRepository) {
        this.gameSubService = gameSubService;
        this.gameRepository = gameRepository;
    }

    /**
     * {@code GET  /game-subs/is-subscribed/:gameId} : Is the logged-in User subscribed to a Game.
     *
     * @param gameId the id of the game.
     * @return the {@link ResponseEntity} with status {@code 200 (Ok)} and with body the Boolean,
     * or with status {@code 400 (Bad Request)} if the gameId is not found.
     */
    @GetMapping("/game-subs/is-subscribed/{gameId}")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable(value = "gameId") Long gameId, HttpServletRequest request) {
        log.debug("REST Request to isSubscribed to Game : {}", gameId);

        if (!gameRepository.existsById(gameId)) {
            throw new BadRequestAlertException("Entity not found", "game", "idnotfound");
        }

        boolean isSubscribe = gameSubService.isSubscribed(request.getRemoteUser(), gameId);

        return ResponseEntity.ok().body(isSubscribe);
    }

    /**
     * {@code POST  /game-subs/subscribe/:gameId} : Subscribes the logged-in User to a Game.
     *
     * @param gameId the id of the game to subscribe.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameSubDTO,
     * or with status {@code 400 (Bad Request)} if the gameId is not found.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-subs/subscribe/{gameId}")
    public ResponseEntity<GameSubDTO> subscribe(@PathVariable(value = "gameId") Long gameId, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST Request to subscribe to Game : {}", gameId);

        if (!gameRepository.existsById(gameId)) {
            throw new BadRequestAlertException("Entity not found", "game", "idnotfound");
        }

        GameSubDTO result = gameSubService.subscribe(request.getRemoteUser(), gameId);

        return ResponseEntity
            .created(new URI("/api/game-subs/" + result.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "Vous êtes maintenant abonné(e)", ""))
            .body(result);
    }

    /**
     * {@code DELETE  /game-subs/unsubscribe/:gameId} : Unsubscribes the logged-in User from a Game.
     *
     * @param gameId the id of the gameSubDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)},
     * or with status {@code 400 (Bad Request)} if the gameId is not found.
     */
    @DeleteMapping("/game-subs/unsubscribe/{gameId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long gameId, HttpServletRequest request) {
        log.debug("REST request to unsubscribe from Game : {}", gameId);

        if (!gameRepository.existsById(gameId)) {
            throw new BadRequestAlertException("Entity not found", "game", "idnotfound");
        }

        gameSubService.unsubscribe(request.getRemoteUser(), gameId);

        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "Vous n'êtes plus abonné(e)", "")).build();
    }
}
