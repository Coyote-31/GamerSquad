package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
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

    EventSubRepositoryExtended eventSubRepository;

    public EventSubServiceExtended(EventSubRepositoryExtended eventSubRepository, EventSubMapper eventSubMapper) {
        super(eventSubRepository, eventSubMapper);
        this.eventSubRepository = eventSubRepository;
    }

    /**
     * Returns true if the user is subscribed and accepted to the event.
     *
     * @param appUser the AppUser.
     * @param event the Event
     * @return true if the user is subscribed and accepted to the event, false otherwise.
     */
    public boolean isAcceptedSubscriber(AppUser appUser, Event event) {
        log.debug("Request isAcceptedSubscriber with AppUser id : {} and Event id : {}", appUser.getId(), event.getId());

        return eventSubRepository.isAcceptedSubscriber(appUser, event);
    }
}
