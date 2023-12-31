package com.coyote.gamersquad.web.rest;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.repository.FriendshipRepository;
import com.coyote.gamersquad.service.FriendshipService;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
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
 * REST controller for managing {@link com.coyote.gamersquad.domain.Friendship}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class FriendshipResource {

    private final Logger log = LoggerFactory.getLogger(FriendshipResource.class);

    private static final String ENTITY_NAME = "friendship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendshipService friendshipService;

    private final FriendshipRepository friendshipRepository;

    public FriendshipResource(FriendshipService friendshipService, FriendshipRepository friendshipRepository) {
        this.friendshipService = friendshipService;
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * {@code POST  /friendships} : Create a new friendship.
     *
     * @param friendshipDTO the friendshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendshipDTO, or with status {@code 400 (Bad Request)} if the friendship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friendships")
    public ResponseEntity<FriendshipDTO> createFriendship(@Valid @RequestBody FriendshipDTO friendshipDTO) throws URISyntaxException {
        log.debug("REST request to save Friendship : {}", friendshipDTO);
        if (friendshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new friendship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendshipDTO result = friendshipService.save(friendshipDTO);
        return ResponseEntity
            .created(new URI("/api/friendships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friendships/:id} : Updates an existing friendship.
     *
     * @param id the id of the friendshipDTO to save.
     * @param friendshipDTO the friendshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendshipDTO,
     * or with status {@code 400 (Bad Request)} if the friendshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friendships/{id}")
    public ResponseEntity<FriendshipDTO> updateFriendship(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FriendshipDTO friendshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Friendship : {}, {}", id, friendshipDTO);
        if (friendshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FriendshipDTO result = friendshipService.update(friendshipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /friendships/:id} : Partial updates given fields of an existing friendship, field will ignore if it is null
     *
     * @param id the id of the friendshipDTO to save.
     * @param friendshipDTO the friendshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendshipDTO,
     * or with status {@code 400 (Bad Request)} if the friendshipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the friendshipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the friendshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/friendships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FriendshipDTO> partialUpdateFriendship(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FriendshipDTO friendshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Friendship partially : {}, {}", id, friendshipDTO);
        if (friendshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FriendshipDTO> result = friendshipService.partialUpdate(friendshipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendshipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /friendships} : get all the friendships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friendships in body.
     */
    @GetMapping("/friendships")
    public List<FriendshipDTO> getAllFriendships() {
        log.debug("REST request to get all Friendships");
        return friendshipService.findAll();
    }

    /**
     * {@code GET  /friendships/:id} : get the "id" friendship.
     *
     * @param id the id of the friendshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friendships/{id}")
    public ResponseEntity<FriendshipDTO> getFriendship(@PathVariable Long id) {
        log.debug("REST request to get Friendship : {}", id);
        Optional<FriendshipDTO> friendshipDTO = friendshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendshipDTO);
    }

    /**
     * {@code DELETE  /friendships/:id} : delete the "id" friendship.
     *
     * @param id the id of the friendshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friendships/{id}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long id) {
        log.debug("REST request to delete Friendship : {}", id);
        friendshipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
