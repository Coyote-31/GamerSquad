package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.domain.dto.projection.PlayerFriendshipDTO;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipChatRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipRepositoryExtended;
import com.coyote.gamersquad.service.FriendshipService;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
import com.coyote.gamersquad.service.errors.AppUserNotFoundException;
import com.coyote.gamersquad.service.errors.ForbiddenException;
import com.coyote.gamersquad.service.errors.FriendshipNotFoundException;
import com.coyote.gamersquad.service.mapper.AppUserMapper;
import com.coyote.gamersquad.service.mapper.FriendshipMapper;
import java.util.List;
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

    private final FriendshipRepositoryExtended friendshipRepository;

    private final FriendshipMapper friendshipMapper;

    private final FriendshipChatRepositoryExtended friendshipChatRepository;

    private final AppUserRepositoryExtended appUserRepository;

    private final AppUserMapper appUserMapper;

    public FriendshipServiceExtended(
        FriendshipRepositoryExtended friendshipRepository,
        FriendshipMapper friendshipMapper,
        FriendshipChatRepositoryExtended friendshipChatRepository,
        AppUserRepositoryExtended appUserRepository,
        AppUserMapper appUserMapper
    ) {
        super(friendshipRepository, friendshipMapper);
        this.friendshipRepository = friendshipRepository;
        this.friendshipMapper = friendshipMapper;
        this.friendshipChatRepository = friendshipChatRepository;
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
    }

    /**
     * Get all AppUsers friends with the user.
     *
     * @param userLogin the login of the user.
     * @return the list of {@link AppUserDTO} friends.
     */
    public List<AppUserDTO> getAllFriends(String userLogin) {
        log.debug("Request to getAllFriends of User : {}", userLogin);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        return appUserMapper.toDto(appUserRepository.findAllFriends(appUser.getId()));
    }

    /**
     * Get all Players friends with the user.
     *
     * @param userLogin the login of the user.
     * @return the list of {@link PlayerFriendshipDTO} friends.
     */
    public List<PlayerFriendshipDTO> getAllPlayersFriends(String userLogin) {
        log.debug("Request to getAllPlayersFriends of User : {}", userLogin);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        return friendshipRepository.getAllPlayersFriends(appUser.getId());
    }

    /**
     *  Get the Player friend with the user by friendshipId.
     *
     * @param friendshipId the ID of the Friendship.
     * @param userLogin the Login of the User.
     * @return the {@link PlayerFriendshipDTO} friend.
     */
    public PlayerFriendshipDTO getPlayerFriendByFriendshipId(Long friendshipId, String userLogin) {
        log.debug("Request to getPlayerFriend with Friendship Id : {} for User : {}", friendshipId, userLogin);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the Friendship exist
        Friendship friendship = friendshipRepository
            .findById(friendshipId)
            .orElseThrow(() -> new FriendshipNotFoundException(friendshipId));

        // Check if the appUser is part of the Friendship
        if (!appUser.equals(friendship.getAppUserOwner()) && !appUser.equals(friendship.getAppUserReceiver())) {
            throw new ForbiddenException("User : " + userLogin + " is not part of the Friendship with ID : " + friendshipId);
        }

        return friendshipRepository.getPlayerFriendByFriendshipId(friendshipId, appUser);
    }

    /**
     * Creates a new friendship demand.
     *
     * @param appUserId the ID of the AppUser receiver.
     * @param userLogin the Login of the User owning the demand.
     * @return the created {@link FriendshipDTO} demand.
     */
    public FriendshipDTO createFriendshipDemand(Long appUserId, String userLogin) {
        log.debug("Request to createFriendshipDemand to AppUser : {} for User : {}", appUserId, userLogin);

        AppUser owner = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        AppUser receiver = appUserRepository.findById(appUserId).orElseThrow(() -> new AppUserNotFoundException(appUserId));

        // Check if friendship entity already exists between both users
        if (friendshipRepository.existsFriendshipBetweenAppUsers(owner, receiver)) {
            throw new ForbiddenException(
                "Friendship between AppUser IDs : " + owner.getId() + " and " + receiver.getId() + " already exists"
            );
        }

        // Create the entity
        Friendship friendshipToSave = new Friendship();
        friendshipToSave.setIsAccepted(false);
        friendshipToSave.setAppUserOwner(owner);
        friendshipToSave.setAppUserReceiver(receiver);

        return friendshipMapper.toDto(friendshipRepository.save(friendshipToSave));
    }

    /**
     * Accepts a friendship demand.
     *
     * @param appUserId the ID of the AppUser owning the demand.
     * @param userLogin the Login of the User receiver accepting the demand.
     * @return the updated {@link FriendshipDTO} demand.
     */
    public FriendshipDTO acceptFriendshipDemand(Long appUserId, String userLogin) {
        log.debug("Request to acceptFriendshipDemand from AppUser : {} for User : {}", appUserId, userLogin);

        AppUser owner = appUserRepository.findById(appUserId).orElseThrow(() -> new AppUserNotFoundException(appUserId));

        AppUser receiver = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the friendship entity exist with the AppUser receiver as receiver and retrieve it
        Friendship friendshipToUpdate = friendshipRepository
            .findByAppUserOwnerAndAppUserReceiver(owner, receiver)
            .orElseThrow(() -> new FriendshipNotFoundException(owner.getId(), receiver.getId()));

        // Check if already accepted
        if (friendshipToUpdate.getIsAccepted()) {
            throw new ForbiddenException(
                "Friendship already accepted between AppUser owner : " + owner.getId() + " and AppUser receiver : " + receiver.getId()
            );
        }

        // Accept the friendship
        friendshipToUpdate.setIsAccepted(true);

        return friendshipMapper.toDto(friendshipRepository.save(friendshipToUpdate));
    }

    /**
     * Deletes friendship between two appUsers
     *
     * @param appUserId the ID of the AppUser to delete friendship.
     * @param userLogin the Login of the User deleting the friendship.
     */
    public void deleteFriendship(Long appUserId, String userLogin) {
        AppUser appUser1 = appUserRepository.findById(appUserId).orElseThrow(() -> new AppUserNotFoundException(appUserId));

        AppUser appUser2 = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new AppUserNotFoundException(userLogin));

        // Check if the friendship entity exist between appUser1 and appUser2 and retrieve it
        Friendship friendshipToDelete = friendshipRepository
            .findFriendshipBetweenAppUsers(appUser1, appUser2)
            .orElseThrow(() -> new FriendshipNotFoundException(appUserId, userLogin));

        // Delete all friendshipChats
        friendshipChatRepository.deleteAllByFriendship_Id(friendshipToDelete.getId());

        // Delete the friendship
        friendshipRepository.delete(friendshipToDelete);
    }
}
