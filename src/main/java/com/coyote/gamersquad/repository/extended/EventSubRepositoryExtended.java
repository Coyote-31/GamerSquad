package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.repository.EventSubRepository;
import com.coyote.gamersquad.service.dto.projection.EventPlayerDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the EventSub entity.
 */
@Repository
public interface EventSubRepositoryExtended extends EventSubRepository {
    @Query(
        "select new com.coyote.gamersquad.service.dto.projection.EventPlayerDTO(" +
        "eventSub.appUser.internalUser.id, " +
        "eventSub.appUser.internalUser.login, " +
        "eventSub.appUser.internalUser.imageUrl, " +
        "eventSub.appUser.id, " +
        "eventSub.event.id, " +
        "eventSub.id, " +
        "eventSub.isAccepted" +
        ") " +
        "from EventSub eventSub " +
        "where eventSub.event = :event " +
        "order by eventSub.appUser.internalUser.login"
    )
    List<EventPlayerDTO> getAllEventPlayersByEvent(@Param("event") Event event);

    @Query("select count(eventSub) > 0 " + "from EventSub eventSub " + "where eventSub.event = :event " + "and eventSub.appUser = :appUser")
    boolean isAlreadySubscribed(@Param("appUser") AppUser appUser, @Param("event") Event event);

    @Query(
        "select count(eventSub) > 0 " +
        "from EventSub eventSub " +
        "where eventSub.event = :event " +
        "and eventSub.appUser = :appUser " +
        "and eventSub.isAccepted = true"
    )
    boolean isAcceptedSubscriber(@Param("appUser") AppUser appUser, @Param("event") Event event);

    @Modifying
    @Query("delete from EventSub eventSub " + "where eventSub.event = :event " + "and eventSub.appUser = :appUser")
    void unsubscribeUserByEventId(@Param("appUser") AppUser appUser, @Param("event") Event event);
}
