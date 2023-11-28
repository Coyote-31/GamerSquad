package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.repository.extended.GameRepositoryExtended;
import com.coyote.gamersquad.service.GameService;
import com.coyote.gamersquad.service.mapper.GameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link Game}.
 */
@Service
@Transactional
public class GameServiceExtended extends GameService {

    private final Logger log = LoggerFactory.getLogger(GameServiceExtended.class);

    public GameServiceExtended(GameRepositoryExtended gameRepository, GameMapper gameMapper) {
        super(gameRepository, gameMapper);
    }
}
