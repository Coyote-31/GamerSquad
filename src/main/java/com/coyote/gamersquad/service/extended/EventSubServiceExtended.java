package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.EventSub;
import com.coyote.gamersquad.repository.extended.EventSubRepositoryExtended;
import com.coyote.gamersquad.service.EventSubService;
import com.coyote.gamersquad.service.mapper.EventSubMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link EventSub}.
 */
@Service
@Transactional
public class EventSubServiceExtended extends EventSubService {

    private final Logger log = LoggerFactory.getLogger(EventSubServiceExtended.class);

    public EventSubServiceExtended(EventSubRepositoryExtended eventSubRepository, EventSubMapper eventSubMapper) {
        super(eventSubRepository, eventSubMapper);
    }
}
