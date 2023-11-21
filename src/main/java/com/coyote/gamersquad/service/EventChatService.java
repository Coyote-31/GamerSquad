package com.coyote.gamersquad.service;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.EventChat;
import com.coyote.gamersquad.repository.EventChatRepository;
import com.coyote.gamersquad.service.dto.EventChatDTO;
import com.coyote.gamersquad.service.mapper.EventChatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventChat}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class EventChatService {

    private final Logger log = LoggerFactory.getLogger(EventChatService.class);

    private final EventChatRepository eventChatRepository;

    private final EventChatMapper eventChatMapper;

    public EventChatService(EventChatRepository eventChatRepository, EventChatMapper eventChatMapper) {
        this.eventChatRepository = eventChatRepository;
        this.eventChatMapper = eventChatMapper;
    }

    /**
     * Save a eventChat.
     *
     * @param eventChatDTO the entity to save.
     * @return the persisted entity.
     */
    public EventChatDTO save(EventChatDTO eventChatDTO) {
        log.debug("Request to save EventChat : {}", eventChatDTO);
        EventChat eventChat = eventChatMapper.toEntity(eventChatDTO);
        eventChat = eventChatRepository.save(eventChat);
        return eventChatMapper.toDto(eventChat);
    }

    /**
     * Update a eventChat.
     *
     * @param eventChatDTO the entity to save.
     * @return the persisted entity.
     */
    public EventChatDTO update(EventChatDTO eventChatDTO) {
        log.debug("Request to update EventChat : {}", eventChatDTO);
        EventChat eventChat = eventChatMapper.toEntity(eventChatDTO);
        eventChat = eventChatRepository.save(eventChat);
        return eventChatMapper.toDto(eventChat);
    }

    /**
     * Partially update a eventChat.
     *
     * @param eventChatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventChatDTO> partialUpdate(EventChatDTO eventChatDTO) {
        log.debug("Request to partially update EventChat : {}", eventChatDTO);

        return eventChatRepository
            .findById(eventChatDTO.getId())
            .map(existingEventChat -> {
                eventChatMapper.partialUpdate(existingEventChat, eventChatDTO);

                return existingEventChat;
            })
            .map(eventChatRepository::save)
            .map(eventChatMapper::toDto);
    }

    /**
     * Get all the eventChats.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EventChatDTO> findAll() {
        log.debug("Request to get all EventChats");
        return eventChatRepository.findAll().stream().map(eventChatMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eventChat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventChatDTO> findOne(Long id) {
        log.debug("Request to get EventChat : {}", id);
        return eventChatRepository.findById(id).map(eventChatMapper::toDto);
    }

    /**
     * Delete the eventChat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventChat : {}", id);
        eventChatRepository.deleteById(id);
    }
}
