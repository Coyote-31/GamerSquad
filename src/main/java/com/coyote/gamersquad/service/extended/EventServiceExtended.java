package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.repository.extended.EventRepositoryExtended;
import com.coyote.gamersquad.service.EventService;
import com.coyote.gamersquad.service.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link Event}.
 */
@Service
@Transactional
public class EventServiceExtended extends EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceExtended.class);

    public EventServiceExtended(EventRepositoryExtended eventRepository, EventMapper eventMapper) {
        super(eventRepository, eventMapper);
    }
}
