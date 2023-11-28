package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.EventChatServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.EventChat}.
 */
@RestController
@RequestMapping("/api/v1")
public class EventChatResource {

    private final Logger log = LoggerFactory.getLogger(EventChatResource.class);

    private static final String ENTITY_NAME = "eventChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventChatServiceExtended eventChatService;

    public EventChatResource(EventChatServiceExtended eventChatService) {
        this.eventChatService = eventChatService;
    }
}
