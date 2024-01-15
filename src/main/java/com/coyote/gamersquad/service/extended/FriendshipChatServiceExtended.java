package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.domain.FriendshipChat;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipChatRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipRepositoryExtended;
import com.coyote.gamersquad.service.FriendshipChatService;
import com.coyote.gamersquad.service.dto.FriendshipChatDTO;
import com.coyote.gamersquad.service.dto.form.FriendMessageDTO;
import com.coyote.gamersquad.service.dto.projection.PlayerChatDTO;
import com.coyote.gamersquad.service.errors.AppUserNotFoundException;
import com.coyote.gamersquad.service.errors.ForbiddenException;
import com.coyote.gamersquad.service.errors.FriendshipNotFoundException;
import com.coyote.gamersquad.service.mapper.FriendshipChatMapper;
import java.time.Instant;
import java.util.List;
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

    FriendshipChatRepositoryExtended friendshipChatRepository;

    FriendshipChatMapper friendshipChatMapper;

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
        this.friendshipChatMapper = friendshipChatMapper;
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
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the friendship exist
        Friendship friendship = friendshipRepository
            .findById(friendshipId)
            .orElseThrow(() -> new FriendshipNotFoundException(friendshipId));

        // Check if the appUser is part of this friendship
        if (!appUser.equals(friendship.getAppUserOwner()) && !appUser.equals(friendship.getAppUserReceiver())) {
            throw new ForbiddenException(
                "Forbidden because appUser with id : " + appUser.getId() + " is not part of the friendship with id : " + friendship.getId()
            );
        }

        return friendshipChatRepository.getAllPlayerChatsByFriendshipId(friendshipId);
    }

    /**
     * Save a new FriendshipChat with a message from the UserLogin with the friendshipId.
     *
     * @param friendMessage the message of the new FriendshipChat.
     * @param friendshipId the id of the Friendship.
     * @param userLogin the login of the User.
     * @return the {@link FriendshipChatDTO} created.
     */
    public FriendshipChatDTO createFriendshipChatMessage(FriendMessageDTO friendMessage, Long friendshipId, String userLogin) {
        log.debug("Request to save FriendshipChat message with friendshipId : {} for User : {}", friendshipId, userLogin);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the friendship exist
        Friendship friendship = friendshipRepository
            .findById(friendshipId)
            .orElseThrow(() -> new FriendshipNotFoundException(friendshipId));

        // Check if the appUser is part of this friendship
        if (!appUser.equals(friendship.getAppUserOwner()) && !appUser.equals(friendship.getAppUserReceiver())) {
            throw new ForbiddenException(
                "Forbidden because appUser with id : " + appUser.getId() + " is not part of the friendship with id : " + friendship.getId()
            );
        }

        // Check if the friendship is activated
        if (!friendship.getIsAccepted()) {
            throw new ForbiddenException("Forbidden because friendship with id : " + friendship.getId() + " is not activated");
        }

        // Create the new friendshipChat to save
        FriendshipChat friendshipChatToSave = new FriendshipChat();
        friendshipChatToSave.message(friendMessage.getMessage()).sendAt(Instant.now()).friendship(friendship).sender(appUser);

        return friendshipChatMapper.toDto(friendshipChatRepository.save(friendshipChatToSave));
    }
}
