package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.FriendshipRepositoryExtended;
import com.coyote.gamersquad.service.FriendshipService;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.projection.PlayerFriendshipDTO;
import com.coyote.gamersquad.service.mapper.AppUserMapper;
import com.coyote.gamersquad.service.mapper.FriendshipMapper;
import java.util.List;
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
}
