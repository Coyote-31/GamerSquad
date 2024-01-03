package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.repository.EventChatRepository;
import com.coyote.gamersquad.service.dto.projection.EventPlayerChatDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the EventChat entity.
 */
@Repository
public interface EventChatRepositoryExtended extends EventChatRepository {
    @Query(
        "select new com.coyote.gamersquad.service.dto.projection.EventPlayerChatDTO(" +
        "eventChat.appUser.internalUser.id, " +
        "eventChat.appUser.internalUser.login, " +
        "eventChat.appUser.internalUser.imageUrl, " +
        "eventChat.appUser.id, " +
        "eventChat.event.id, " +
        "eventChat.id, " +
        "eventChat.message, " +
        "eventChat.sendAt" +
        ") " +
        "from EventChat eventChat " +
        "where eventChat.event = :event " +
        "order by eventChat.sendAt desc"
    )
    List<EventPlayerChatDTO> getAllEventPlayerChatsByEvent(@Param("event") Event event);
}
