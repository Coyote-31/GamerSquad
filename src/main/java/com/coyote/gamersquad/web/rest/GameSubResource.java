package com.coyote.gamersquad.web.rest;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.repository.GameSubRepository;
import com.coyote.gamersquad.service.GameSubService;
import com.coyote.gamersquad.service.dto.GameSubDTO;
import com.coyote.gamersquad.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.coyote.gamersquad.domain.GameSub}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class GameSubResource {

    private final Logger log = LoggerFactory.getLogger(GameSubResource.class);

    private static final String ENTITY_NAME = "gameSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameSubService gameSubService;

    private final GameSubRepository gameSubRepository;

    public GameSubResource(GameSubService gameSubService, GameSubRepository gameSubRepository) {
        this.gameSubService = gameSubService;
        this.gameSubRepository = gameSubRepository;
    }

    /**
     * {@code POST  /game-subs} : Create a new gameSub.
     *
     * @param gameSubDTO the gameSubDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameSubDTO, or with status {@code 400 (Bad Request)} if the gameSub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-subs")
    public ResponseEntity<GameSubDTO> createGameSub(@Valid @RequestBody GameSubDTO gameSubDTO) throws URISyntaxException {
        log.debug("REST request to save GameSub : {}", gameSubDTO);
        if (gameSubDTO.getId() != null) {
            throw new BadRequestAlertException("A new gameSub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameSubDTO result = gameSubService.save(gameSubDTO);
        return ResponseEntity
            .created(new URI("/api/game-subs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-subs/:id} : Updates an existing gameSub.
     *
     * @param id the id of the gameSubDTO to save.
     * @param gameSubDTO the gameSubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameSubDTO,
     * or with status {@code 400 (Bad Request)} if the gameSubDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameSubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-subs/{id}")
    public ResponseEntity<GameSubDTO> updateGameSub(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GameSubDTO gameSubDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GameSub : {}, {}", id, gameSubDTO);
        if (gameSubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameSubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameSubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GameSubDTO result = gameSubService.update(gameSubDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameSubDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-subs/:id} : Partial updates given fields of an existing gameSub, field will ignore if it is null
     *
     * @param id the id of the gameSubDTO to save.
     * @param gameSubDTO the gameSubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameSubDTO,
     * or with status {@code 400 (Bad Request)} if the gameSubDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gameSubDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gameSubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-subs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GameSubDTO> partialUpdateGameSub(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GameSubDTO gameSubDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GameSub partially : {}, {}", id, gameSubDTO);
        if (gameSubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameSubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameSubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GameSubDTO> result = gameSubService.partialUpdate(gameSubDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameSubDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /game-subs} : get all the gameSubs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameSubs in body.
     */
    @GetMapping("/game-subs")
    public List<GameSubDTO> getAllGameSubs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all GameSubs");
        return gameSubService.findAll();
    }

    /**
     * {@code GET  /game-subs/:id} : get the "id" gameSub.
     *
     * @param id the id of the gameSubDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameSubDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-subs/{id}")
    public ResponseEntity<GameSubDTO> getGameSub(@PathVariable Long id) {
        log.debug("REST request to get GameSub : {}", id);
        Optional<GameSubDTO> gameSubDTO = gameSubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameSubDTO);
    }

    /**
     * {@code DELETE  /game-subs/:id} : delete the "id" gameSub.
     *
     * @param id the id of the gameSubDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-subs/{id}")
    public ResponseEntity<Void> deleteGameSub(@PathVariable Long id) {
        log.debug("REST request to delete GameSub : {}", id);
        gameSubService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
