package com.coyote.gamersquad.web.rest;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.repository.EventSubRepository;
import com.coyote.gamersquad.service.EventSubService;
import com.coyote.gamersquad.service.dto.EventSubDTO;
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
 * REST controller for managing {@link com.coyote.gamersquad.domain.EventSub}.
 */
@RestController
@RequestMapping("/api")
@GeneratedByJHipster
public class EventSubResource {

    private final Logger log = LoggerFactory.getLogger(EventSubResource.class);

    private static final String ENTITY_NAME = "eventSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventSubService eventSubService;

    private final EventSubRepository eventSubRepository;

    public EventSubResource(EventSubService eventSubService, EventSubRepository eventSubRepository) {
        this.eventSubService = eventSubService;
        this.eventSubRepository = eventSubRepository;
    }

    /**
     * {@code POST  /event-subs} : Create a new eventSub.
     *
     * @param eventSubDTO the eventSubDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventSubDTO, or with status {@code 400 (Bad Request)} if the eventSub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-subs")
    public ResponseEntity<EventSubDTO> createEventSub(@Valid @RequestBody EventSubDTO eventSubDTO) throws URISyntaxException {
        log.debug("REST request to save EventSub : {}", eventSubDTO);
        if (eventSubDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventSub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventSubDTO result = eventSubService.save(eventSubDTO);
        return ResponseEntity
            .created(new URI("/api/event-subs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-subs/:id} : Updates an existing eventSub.
     *
     * @param id the id of the eventSubDTO to save.
     * @param eventSubDTO the eventSubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventSubDTO,
     * or with status {@code 400 (Bad Request)} if the eventSubDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventSubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-subs/{id}")
    public ResponseEntity<EventSubDTO> updateEventSub(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventSubDTO eventSubDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventSub : {}, {}", id, eventSubDTO);
        if (eventSubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventSubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventSubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventSubDTO result = eventSubService.update(eventSubDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventSubDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-subs/:id} : Partial updates given fields of an existing eventSub, field will ignore if it is null
     *
     * @param id the id of the eventSubDTO to save.
     * @param eventSubDTO the eventSubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventSubDTO,
     * or with status {@code 400 (Bad Request)} if the eventSubDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventSubDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventSubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-subs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventSubDTO> partialUpdateEventSub(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventSubDTO eventSubDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventSub partially : {}, {}", id, eventSubDTO);
        if (eventSubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventSubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventSubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventSubDTO> result = eventSubService.partialUpdate(eventSubDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventSubDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-subs} : get all the eventSubs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventSubs in body.
     */
    @GetMapping("/event-subs")
    public List<EventSubDTO> getAllEventSubs() {
        log.debug("REST request to get all EventSubs");
        return eventSubService.findAll();
    }

    /**
     * {@code GET  /event-subs/:id} : get the "id" eventSub.
     *
     * @param id the id of the eventSubDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventSubDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-subs/{id}")
    public ResponseEntity<EventSubDTO> getEventSub(@PathVariable Long id) {
        log.debug("REST request to get EventSub : {}", id);
        Optional<EventSubDTO> eventSubDTO = eventSubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventSubDTO);
    }

    /**
     * {@code DELETE  /event-subs/:id} : delete the "id" eventSub.
     *
     * @param id the id of the eventSubDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-subs/{id}")
    public ResponseEntity<Void> deleteEventSub(@PathVariable Long id) {
        log.debug("REST request to delete EventSub : {}", id);
        eventSubService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
