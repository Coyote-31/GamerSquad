package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.EventSubServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.EventSub}.
 */
@RestController
@RequestMapping("/api/v1")
public class EventSubResourceV1 {

    private final Logger log = LoggerFactory.getLogger(EventSubResourceV1.class);

    private static final String ENTITY_NAME = "eventSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventSubServiceExtended eventSubService;

    public EventSubResourceV1(EventSubServiceExtended eventSubService) {
        this.eventSubService = eventSubService;
    }
}
