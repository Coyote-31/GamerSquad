package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.domain.dto.projection.EventDetailDTO;
import com.coyote.gamersquad.repository.EventRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the Event entity.
 */
@Repository
public interface EventRepositoryExtended extends EventRepository {
    @Query(
        "select new com.coyote.gamersquad.domain.dto.projection.EventDetailDTO(" +
        "event.id, " +
        "event.title, " +
        "event.description, " +
        "event.meetingDate, " +
        "event.isPrivate, " +
        "event.owner.internalUser.login, " +
        "event.owner.internalUser.imageUrl, " +
        "game.id," +
        "game.title, " +
        "game.imgUrl" +
        ") " +
        "from Event event " +
        "join Game game on event.game = game " +
        "where game = :game " +
        "and event.isPrivate = false " +
        "and event.meetingDate > current_time() " +
        "order by event.meetingDate"
    )
    List<EventDetailDTO> getAllEventDetailsPublicByGameId(@Param("game") Game game);

    @Query(
        "select new com.coyote.gamersquad.domain.dto.projection.EventDetailDTO(" +
        "event.id, " +
        "event.title, " +
        "event.description, " +
        "event.meetingDate, " +
        "event.isPrivate, " +
        "event.owner.internalUser.login, " +
        "event.owner.internalUser.imageUrl, " +
        "event.game.id," +
        "event.game.title, " +
        "event.game.imgUrl" +
        ") " +
        "from Event event " +
        "where event.id = :eventId"
    )
    EventDetailDTO getEventDetailByEventId(@Param("eventId") Long eventId);

    @Query(
        "select new com.coyote.gamersquad.domain.dto.projection.EventDetailDTO(" +
        "event.id, " +
        "event.title, " +
        "event.description, " +
        "event.meetingDate, " +
        "event.isPrivate, " +
        "event.owner.internalUser.login, " +
        "event.owner.internalUser.imageUrl, " +
        "event.game.id," +
        "event.game.title, " +
        "event.game.imgUrl" +
        ") " +
        "from Event event " +
        "where event.owner = :appUser " +
        "order by event.meetingDate"
    )
    List<EventDetailDTO> getAllEventDetailsOwnedByAppUser(@Param("appUser") AppUser appUser);

    @Query(
        "select new com.coyote.gamersquad.domain.dto.projection.EventDetailDTO(" +
        "event.id, " +
        "event.title, " +
        "event.description, " +
        "event.meetingDate, " +
        "event.isPrivate, " +
        "event.owner.internalUser.login, " +
        "event.owner.internalUser.imageUrl, " +
        "event.game.id," +
        "event.game.title, " +
        "event.game.imgUrl" +
        ") " +
        "from Event event " +
        "join EventSub eventSub on event = eventSub.event " +
        "where eventSub.appUser = :appUser " +
        "and eventSub.isAccepted = true " +
        "order by event.meetingDate"
    )
    List<EventDetailDTO> getAllEventDetailsSubscribedByAppUser(@Param("appUser") AppUser appUser);

    @Query(
        "select new com.coyote.gamersquad.domain.dto.projection.EventDetailDTO(" +
        "event.id, " +
        "event.title, " +
        "event.description, " +
        "event.meetingDate, " +
        "event.isPrivate, " +
        "event.owner.internalUser.login, " +
        "event.owner.internalUser.imageUrl, " +
        "event.game.id," +
        "event.game.title, " +
        "event.game.imgUrl" +
        ") " +
        "from Event event " +
        "join EventSub eventSub on event = eventSub.event " +
        "where eventSub.appUser = :appUser " +
        "and eventSub.isAccepted = false " +
        "order by event.meetingDate"
    )
    List<EventDetailDTO> getAllEventDetailsPendingByAppUser(@Param("appUser") AppUser appUser);
}
