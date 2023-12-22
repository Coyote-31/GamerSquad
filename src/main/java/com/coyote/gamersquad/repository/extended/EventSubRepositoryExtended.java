package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.repository.EventSubRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the EventSub entity.
 */
@Repository
public interface EventSubRepositoryExtended extends EventSubRepository {
    @Query(
        "select count(eventSub) > 0 " +
        "from EventSub eventSub " +
        "where eventSub.event = :event " +
        "and eventSub.appUser = :appUser " +
        "and eventSub.isAccepted = true"
    )
    boolean isAcceptedSubscriber(@Param("appUser") AppUser appUser, @Param("event") Event event);
}
