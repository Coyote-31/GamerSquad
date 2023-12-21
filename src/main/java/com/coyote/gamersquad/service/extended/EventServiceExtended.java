package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventRepositoryExtended;
import com.coyote.gamersquad.repository.extended.GameRepositoryExtended;
import com.coyote.gamersquad.service.EventService;
import com.coyote.gamersquad.service.dto.projection.EventDetailDTO;
import com.coyote.gamersquad.service.mapper.EventMapper;
import java.util.List;
import javax.persistence.EntityNotFoundException;
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

    EventRepositoryExtended eventRepository;

    AppUserRepositoryExtended appUserRepository;

    GameRepositoryExtended gameRepository;

    public EventServiceExtended(
        EventRepositoryExtended eventRepository,
        EventMapper eventMapper,
        AppUserRepositoryExtended appUserRepository,
        GameRepositoryExtended gameRepository
    ) {
        super(eventRepository, eventMapper);
        this.eventRepository = eventRepository;
        this.appUserRepository = appUserRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Get all EventDetails public by Game id.
     * Only where meeting date is after now.
     *
     * @param gameId the game id.
     * @return the list of {@link EventDetailDTO}.
     */
    public List<EventDetailDTO> getAllEventDetailsPublicByGameId(Long gameId) {
        log.debug("request to get all EventDetails public by Game id : {}", gameId);

        // Check if a Game exists with this id
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found with ID : " + gameId));

        return eventRepository.getAllEventDetailsPublicByGameId(game);
    }
}
