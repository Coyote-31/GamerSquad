package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.repository.extended.FriendshipRepositoryExtended;
import com.coyote.gamersquad.service.FriendshipService;
import com.coyote.gamersquad.service.mapper.FriendshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link Friendship}.
 */
@Service
@Transactional
public class FriendshipServiceExtended extends FriendshipService {

    private final Logger log = LoggerFactory.getLogger(FriendshipServiceExtended.class);

    public FriendshipServiceExtended(FriendshipRepositoryExtended friendshipRepository, FriendshipMapper friendshipMapper) {
        super(friendshipRepository, friendshipMapper);
    }
}
