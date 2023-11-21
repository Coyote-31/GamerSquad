package com.coyote.gamersquad.service;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.EventSub;
import com.coyote.gamersquad.repository.EventSubRepository;
import com.coyote.gamersquad.service.dto.EventSubDTO;
import com.coyote.gamersquad.service.mapper.EventSubMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventSub}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class EventSubService {

    private final Logger log = LoggerFactory.getLogger(EventSubService.class);

    private final EventSubRepository eventSubRepository;

    private final EventSubMapper eventSubMapper;

    public EventSubService(EventSubRepository eventSubRepository, EventSubMapper eventSubMapper) {
        this.eventSubRepository = eventSubRepository;
        this.eventSubMapper = eventSubMapper;
    }

    /**
     * Save a eventSub.
     *
     * @param eventSubDTO the entity to save.
     * @return the persisted entity.
     */
    public EventSubDTO save(EventSubDTO eventSubDTO) {
        log.debug("Request to save EventSub : {}", eventSubDTO);
        EventSub eventSub = eventSubMapper.toEntity(eventSubDTO);
        eventSub = eventSubRepository.save(eventSub);
        return eventSubMapper.toDto(eventSub);
    }

    /**
     * Update a eventSub.
     *
     * @param eventSubDTO the entity to save.
     * @return the persisted entity.
     */
    public EventSubDTO update(EventSubDTO eventSubDTO) {
        log.debug("Request to update EventSub : {}", eventSubDTO);
        EventSub eventSub = eventSubMapper.toEntity(eventSubDTO);
        eventSub = eventSubRepository.save(eventSub);
        return eventSubMapper.toDto(eventSub);
    }

    /**
     * Partially update a eventSub.
     *
     * @param eventSubDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventSubDTO> partialUpdate(EventSubDTO eventSubDTO) {
        log.debug("Request to partially update EventSub : {}", eventSubDTO);

        return eventSubRepository
            .findById(eventSubDTO.getId())
            .map(existingEventSub -> {
                eventSubMapper.partialUpdate(existingEventSub, eventSubDTO);

                return existingEventSub;
            })
            .map(eventSubRepository::save)
            .map(eventSubMapper::toDto);
    }

    /**
     * Get all the eventSubs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EventSubDTO> findAll() {
        log.debug("Request to get all EventSubs");
        return eventSubRepository.findAll().stream().map(eventSubMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eventSub by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventSubDTO> findOne(Long id) {
        log.debug("Request to get EventSub : {}", id);
        return eventSubRepository.findById(id).map(eventSubMapper::toDto);
    }

    /**
     * Delete the eventSub by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventSub : {}", id);
        eventSubRepository.deleteById(id);
    }
}
