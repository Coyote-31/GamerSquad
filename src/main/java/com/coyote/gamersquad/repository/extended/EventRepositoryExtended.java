package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.repository.EventRepository;
import com.coyote.gamersquad.service.dto.projection.EventDetailDTO;
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
        "select new com.coyote.gamersquad.service.dto.projection.EventDetailDTO(" +
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
}
