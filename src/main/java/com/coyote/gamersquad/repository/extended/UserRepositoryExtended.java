package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.User;
import com.coyote.gamersquad.repository.UserRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the {@link User} entity.
 */
@Repository
public interface UserRepositoryExtended extends UserRepository {}
