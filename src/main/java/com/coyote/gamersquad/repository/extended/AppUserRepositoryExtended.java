package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.repository.AppUserRepository;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the AppUser entity.
 */
@Repository
public interface AppUserRepositoryExtended extends AppUserRepository {
    void deleteByInternalUser_Login(@NotNull String internalUser_login);

    Optional<AppUser> getAppUserByInternalUser_Login(String internalUser_login);
}
