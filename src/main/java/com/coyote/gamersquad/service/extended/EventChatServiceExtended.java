package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.EventChat;
import com.coyote.gamersquad.repository.extended.EventChatRepositoryExtended;
import com.coyote.gamersquad.service.EventChatService;
import com.coyote.gamersquad.service.mapper.EventChatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link EventChat}.
 */
@Service
@Transactional
public class EventChatServiceExtended extends EventChatService {

    private final Logger log = LoggerFactory.getLogger(EventChatServiceExtended.class);

    public EventChatServiceExtended(EventChatRepositoryExtended eventChatRepository, EventChatMapper eventChatMapper) {
        super(eventChatRepository, eventChatMapper);
    }
}
