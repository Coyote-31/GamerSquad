package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.GameSub;
import com.coyote.gamersquad.repository.extended.GameSubRepositoryExtended;
import com.coyote.gamersquad.service.GameSubService;
import com.coyote.gamersquad.service.mapper.GameSubMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link GameSub}.
 */
@Service
@Transactional
public class GameSubServiceExtended extends GameSubService {

    private final Logger log = LoggerFactory.getLogger(GameSubServiceExtended.class);

    public GameSubServiceExtended(GameSubRepositoryExtended gameSubRepository, GameSubMapper gameSubMapper) {
        super(gameSubRepository, gameSubMapper);
    }
}
