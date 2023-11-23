package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.User;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.service.AppUserService;
import com.coyote.gamersquad.service.mapper.AppUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link AppUser}.
 */
@Service
@Transactional
public class AppUserServiceExtended extends AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserServiceExtended.class);

    private final AppUserRepositoryExtended appUserRepository;

    public AppUserServiceExtended(AppUserRepositoryExtended appUserRepository, AppUserMapper appUserMapper) {
        super(appUserRepository, appUserMapper);
        this.appUserRepository = appUserRepository;
    }

    /**
     * Save a appUser from a user.
     *
     * @param user the entity to save from.
     */
    public void registerAppUser(User user) {
        log.debug("Request to register AppUser from User : {}", user);
        AppUser newAppUser = new AppUser();
        newAppUser.setInternalUser(user);
        appUserRepository.save(newAppUser);
    }

    /**
     * Delete a appUser from a user login.
     *
     * @param login the internalUser login to delete from.
     */
    public void delete(String login) {
        log.debug("Request to delete AppUser from internalUser login : {}", login);
        appUserRepository.deleteByInternalUser_Login(login);
    }
}
