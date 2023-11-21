package com.coyote.gamersquad.repository;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.EventChat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventChat entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface EventChatRepository extends JpaRepository<EventChat, Long> {}
