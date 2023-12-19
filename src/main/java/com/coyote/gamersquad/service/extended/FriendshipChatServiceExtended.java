package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.domain.FriendshipChat;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipChatRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipRepositoryExtended;
import com.coyote.gamersquad.service.FriendshipChatService;
import com.coyote.gamersquad.service.dto.projection.PlayerChatDTO;
import com.coyote.gamersquad.service.mapper.FriendshipChatMapper;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link FriendshipChat}.
 */
@Service
@Transactional
public class FriendshipChatServiceExtended extends FriendshipChatService {

    private final Logger log = LoggerFactory.getLogger(FriendshipChatServiceExtended.class);

    FriendshipChatRepositoryExtended friendshipChatRepository;

    AppUserRepositoryExtended appUserRepository;

    FriendshipRepositoryExtended friendshipRepository;

    public FriendshipChatServiceExtended(
        FriendshipChatRepositoryExtended friendshipChatRepository,
        FriendshipChatMapper friendshipChatMapper,
        AppUserRepositoryExtended appUserRepository,
        FriendshipRepositoryExtended friendshipRepository
    ) {
        super(friendshipChatRepository, friendshipChatMapper);
        this.friendshipChatRepository = friendshipChatRepository;
        this.appUserRepository = appUserRepository;
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * Get all the PlayerChats by friendshipId related to the userLogin.
     *
     * @param friendshipId the id of the friendship.
     * @param userLogin the login of user requesting.
     * @return list of {@link PlayerChatDTO}
     */
    public List<PlayerChatDTO> getAllPlayerChatsByFriendshipId(Long friendshipId, String userLogin) {
        log.debug("Request to getAllPlayerChats with FriendshipId : {} for User : {}", friendshipId, userLogin);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        // Check if the friendship exist
        Friendship friendship = friendshipRepository
            .findById(friendshipId)
            .orElseThrow(() -> new EntityNotFoundException("Friendship not found with id : " + friendshipId));

        // Check if the appUser is part of this friendship
        if (!appUser.equals(friendship.getAppUserOwner()) && !appUser.equals(friendship.getAppUserReceiver())) {
            throw new AccessDeniedException(
                "Access Denied because appUser with id : " +
                appUser.getId() +
                " is not part of the friendship with id : " +
                friendship.getId()
            );
        }

        return friendshipChatRepository.getAllPlayerChatsByFriendshipId(friendshipId);
    }
}
