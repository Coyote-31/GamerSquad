package com.coyote.gamersquad.web.rest;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.repository.EventChatRepository;
import com.coyote.gamersquad.service.EventChatService;
import com.coyote.gamersquad.service.dto.EventChatDTO;
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
 * REST controller for managing {@link com.coyote.gamersquad.domain.EventChat}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class EventChatResource {

    private final Logger log = LoggerFactory.getLogger(EventChatResource.class);

    private static final String ENTITY_NAME = "eventChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventChatService eventChatService;

    private final EventChatRepository eventChatRepository;

    public EventChatResource(EventChatService eventChatService, EventChatRepository eventChatRepository) {
        this.eventChatService = eventChatService;
        this.eventChatRepository = eventChatRepository;
    }

    /**
     * {@code POST  /event-chats} : Create a new eventChat.
     *
     * @param eventChatDTO the eventChatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventChatDTO, or with status {@code 400 (Bad Request)} if the eventChat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-chats")
    public ResponseEntity<EventChatDTO> createEventChat(@Valid @RequestBody EventChatDTO eventChatDTO) throws URISyntaxException {
        log.debug("REST request to save EventChat : {}", eventChatDTO);
        if (eventChatDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventChat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventChatDTO result = eventChatService.save(eventChatDTO);
        return ResponseEntity
            .created(new URI("/api/event-chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-chats/:id} : Updates an existing eventChat.
     *
     * @param id the id of the eventChatDTO to save.
     * @param eventChatDTO the eventChatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventChatDTO,
     * or with status {@code 400 (Bad Request)} if the eventChatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventChatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-chats/{id}")
    public ResponseEntity<EventChatDTO> updateEventChat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventChatDTO eventChatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventChat : {}, {}", id, eventChatDTO);
        if (eventChatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventChatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventChatDTO result = eventChatService.update(eventChatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventChatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-chats/:id} : Partial updates given fields of an existing eventChat, field will ignore if it is null
     *
     * @param id the id of the eventChatDTO to save.
     * @param eventChatDTO the eventChatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventChatDTO,
     * or with status {@code 400 (Bad Request)} if the eventChatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventChatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventChatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-chats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventChatDTO> partialUpdateEventChat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventChatDTO eventChatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventChat partially : {}, {}", id, eventChatDTO);
        if (eventChatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventChatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventChatDTO> result = eventChatService.partialUpdate(eventChatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventChatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-chats} : get all the eventChats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventChats in body.
     */
    @GetMapping("/event-chats")
    public List<EventChatDTO> getAllEventChats() {
        log.debug("REST request to get all EventChats");
        return eventChatService.findAll();
    }

    /**
     * {@code GET  /event-chats/:id} : get the "id" eventChat.
     *
     * @param id the id of the eventChatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventChatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-chats/{id}")
    public ResponseEntity<EventChatDTO> getEventChat(@PathVariable Long id) {
        log.debug("REST request to get EventChat : {}", id);
        Optional<EventChatDTO> eventChatDTO = eventChatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventChatDTO);
    }

    /**
     * {@code DELETE  /event-chats/:id} : delete the "id" eventChat.
     *
     * @param id the id of the eventChatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-chats/{id}")
    public ResponseEntity<Void> deleteEventChat(@PathVariable Long id) {
        log.debug("REST request to delete EventChat : {}", id);
        eventChatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
