package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.domain.GameSub;
import com.coyote.gamersquad.repository.GameSubRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the GameSub entity.
 */
@Repository
public interface GameSubRepositoryExtended extends GameSubRepository {
    boolean existsByAppUserAndGame(AppUser appUser, Game game);

    Optional<GameSub> findOneByAppUserAndGame(AppUser appUser, Game game);
}
