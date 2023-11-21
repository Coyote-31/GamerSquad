package com.coyote.gamersquad.web.rest;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.repository.FriendshipChatRepository;
import com.coyote.gamersquad.service.FriendshipChatService;
import com.coyote.gamersquad.service.dto.FriendshipChatDTO;
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
 * REST controller for managing {@link com.coyote.gamersquad.domain.FriendshipChat}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class FriendshipChatResource {

    private final Logger log = LoggerFactory.getLogger(FriendshipChatResource.class);

    private static final String ENTITY_NAME = "friendshipChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendshipChatService friendshipChatService;

    private final FriendshipChatRepository friendshipChatRepository;

    public FriendshipChatResource(FriendshipChatService friendshipChatService, FriendshipChatRepository friendshipChatRepository) {
        this.friendshipChatService = friendshipChatService;
        this.friendshipChatRepository = friendshipChatRepository;
    }

    /**
     * {@code POST  /friendship-chats} : Create a new friendshipChat.
     *
     * @param friendshipChatDTO the friendshipChatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendshipChatDTO, or with status {@code 400 (Bad Request)} if the friendshipChat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friendship-chats")
    public ResponseEntity<FriendshipChatDTO> createFriendshipChat(@Valid @RequestBody FriendshipChatDTO friendshipChatDTO)
        throws URISyntaxException {
        log.debug("REST request to save FriendshipChat : {}", friendshipChatDTO);
        if (friendshipChatDTO.getId() != null) {
            throw new BadRequestAlertException("A new friendshipChat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendshipChatDTO result = friendshipChatService.save(friendshipChatDTO);
        return ResponseEntity
            .created(new URI("/api/friendship-chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friendship-chats/:id} : Updates an existing friendshipChat.
     *
     * @param id the id of the friendshipChatDTO to save.
     * @param friendshipChatDTO the friendshipChatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendshipChatDTO,
     * or with status {@code 400 (Bad Request)} if the friendshipChatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendshipChatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friendship-chats/{id}")
    public ResponseEntity<FriendshipChatDTO> updateFriendshipChat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FriendshipChatDTO friendshipChatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FriendshipChat : {}, {}", id, friendshipChatDTO);
        if (friendshipChatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendshipChatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendshipChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FriendshipChatDTO result = friendshipChatService.update(friendshipChatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendshipChatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /friendship-chats/:id} : Partial updates given fields of an existing friendshipChat, field will ignore if it is null
     *
     * @param id the id of the friendshipChatDTO to save.
     * @param friendshipChatDTO the friendshipChatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendshipChatDTO,
     * or with status {@code 400 (Bad Request)} if the friendshipChatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the friendshipChatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the friendshipChatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/friendship-chats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FriendshipChatDTO> partialUpdateFriendshipChat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FriendshipChatDTO friendshipChatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FriendshipChat partially : {}, {}", id, friendshipChatDTO);
        if (friendshipChatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendshipChatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendshipChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FriendshipChatDTO> result = friendshipChatService.partialUpdate(friendshipChatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendshipChatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /friendship-chats} : get all the friendshipChats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friendshipChats in body.
     */
    @GetMapping("/friendship-chats")
    public List<FriendshipChatDTO> getAllFriendshipChats() {
        log.debug("REST request to get all FriendshipChats");
        return friendshipChatService.findAll();
    }

    /**
     * {@code GET  /friendship-chats/:id} : get the "id" friendshipChat.
     *
     * @param id the id of the friendshipChatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendshipChatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friendship-chats/{id}")
    public ResponseEntity<FriendshipChatDTO> getFriendshipChat(@PathVariable Long id) {
        log.debug("REST request to get FriendshipChat : {}", id);
        Optional<FriendshipChatDTO> friendshipChatDTO = friendshipChatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendshipChatDTO);
    }

    /**
     * {@code DELETE  /friendship-chats/:id} : delete the "id" friendshipChat.
     *
     * @param id the id of the friendshipChatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friendship-chats/{id}")
    public ResponseEntity<Void> deleteFriendshipChat(@PathVariable Long id) {
        log.debug("REST request to delete FriendshipChat : {}", id);
        friendshipChatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
