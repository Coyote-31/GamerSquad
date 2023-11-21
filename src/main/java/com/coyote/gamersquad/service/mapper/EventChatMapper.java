package com.coyote.gamersquad.service.mapper;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.EventChat;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.EventChatDTO;
import com.coyote.gamersquad.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventChat} and its DTO {@link EventChatDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface EventChatMapper extends EntityMapper<EventChatDTO, EventChat> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    EventChatDTO toDto(EventChat s);

    @Named("eventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDTO toDtoEventId(Event event);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
