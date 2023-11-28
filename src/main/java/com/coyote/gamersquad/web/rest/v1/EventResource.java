package com.coyote.gamersquad.web.rest.v1;

import com.coyote.gamersquad.service.extended.EventServiceExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api v1 : REST controller for managing {@link com.coyote.gamersquad.domain.Event}.
 */
@RestController
@RequestMapping("/api/v1")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "event";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventServiceExtended eventService;

    public EventResource(EventServiceExtended eventService) {
        this.eventService = eventService;
    }
}
