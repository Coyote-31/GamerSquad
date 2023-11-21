package com.coyote.gamersquad.repository;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.Game;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Game entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface GameRepository extends JpaRepository<Game, Long> {}
