package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.repository.AppUserRepository;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the AppUser entity.
 */
@Repository
@GeneratedByJHipster
public interface AppUserRepositoryExtended extends AppUserRepository {
    void deleteByInternalUser_Login(@NotNull String internalUser_login);
}
