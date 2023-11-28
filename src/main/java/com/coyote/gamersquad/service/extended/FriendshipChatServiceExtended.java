package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.FriendshipChat;
import com.coyote.gamersquad.repository.extended.FriendshipChatRepositoryExtended;
import com.coyote.gamersquad.service.FriendshipChatService;
import com.coyote.gamersquad.service.mapper.FriendshipChatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link FriendshipChat}.
 */
@Service
@Transactional
public class FriendshipChatServiceExtended extends FriendshipChatService {

    private final Logger log = LoggerFactory.getLogger(FriendshipChatServiceExtended.class);

    public FriendshipChatServiceExtended(
        FriendshipChatRepositoryExtended friendshipChatRepository,
        FriendshipChatMapper friendshipChatMapper
    ) {
        super(friendshipChatRepository, friendshipChatMapper);
    }
}
