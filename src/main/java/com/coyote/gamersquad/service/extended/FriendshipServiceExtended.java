package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipRepositoryExtended;
import com.coyote.gamersquad.service.FriendshipService;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
import com.coyote.gamersquad.service.dto.projection.PlayerFriendshipDTO;
import com.coyote.gamersquad.service.mapper.AppUserMapper;
import com.coyote.gamersquad.service.mapper.FriendshipMapper;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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

    private final AppUserRepositoryExtended appUserRepository;

    private final AppUserMapper appUserMapper;

    public FriendshipServiceExtended(
        FriendshipRepositoryExtended friendshipRepository,
        FriendshipMapper friendshipMapper,
        AppUserRepositoryExtended appUserRepository,
        AppUserMapper appUserMapper
    ) {
        super(friendshipRepository, friendshipMapper);
        this.friendshipRepository = friendshipRepository;
        this.friendshipMapper = friendshipMapper;
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
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

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
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));

        return friendshipRepository.getAllPlayersFriends(appUser.getId());
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
            .orElseThrow(() -> new EntityNotFoundException("AppUser owner not found for login : " + userLogin));

        AppUser receiver = appUserRepository
            .findById(appUserId)
            .orElseThrow(() -> new EntityNotFoundException("AppUser receiver not found for ID : " + appUserId));

        // Check if friendship entity already exists between both users
        if (friendshipRepository.existsFriendshipBetweenAppUsers(owner, receiver)) {
            throw new EntityExistsException(
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

        AppUser owner = appUserRepository
            .findById(appUserId)
            .orElseThrow(() -> new EntityNotFoundException("AppUser owner not found for ID : " + appUserId));

        AppUser receiver = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser receiver not found for login : " + userLogin));

        // Check if the friendship entity exist with the AppUser receiver as receiver and retrieve it
        Friendship friendshipToUpdate = friendshipRepository
            .findByAppUserOwnerAndAppUserReceiver(owner, receiver)
            .orElseThrow(() ->
                new EntityNotFoundException(
                    "Friendship not found with AppUser owner : " + owner.getId() + " and AppUser receiver : " + receiver.getId()
                )
            );

        // Check if already accepted
        if (friendshipToUpdate.getIsAccepted()) {
            throw new IllegalStateException(
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
        AppUser appUser1 = appUserRepository
            .findById(appUserId)
            .orElseThrow(() -> new EntityNotFoundException("AppUser appUser1 not found for ID : " + appUserId));

        AppUser appUser2 = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser appUser2 not found for login : " + userLogin));

        // Check if the friendship entity exist between appUser1 and appUser2 and retrieve it
        Friendship friendshipToDelete = friendshipRepository
            .findFriendshipBetweenAppUsers(appUser1, appUser2)
            .orElseThrow(() ->
                new EntityNotFoundException("Friendship not found between appUser1 : " + appUserId + " and appUser2 : " + userLogin)
            );

        friendshipRepository.delete(friendshipToDelete);
    }
}
