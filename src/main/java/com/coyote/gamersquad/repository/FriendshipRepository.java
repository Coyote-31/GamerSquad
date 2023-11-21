package com.coyote.gamersquad.repository;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.Friendship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Friendship entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {}
