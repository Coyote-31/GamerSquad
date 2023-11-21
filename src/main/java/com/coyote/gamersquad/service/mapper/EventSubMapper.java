package com.coyote.gamersquad.service.mapper;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.EventSub;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.EventDTO;
import com.coyote.gamersquad.service.dto.EventSubDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventSub} and its DTO {@link EventSubDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface EventSubMapper extends EntityMapper<EventSubDTO, EventSub> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    EventSubDTO toDto(EventSub s);

    @Named("eventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDTO toDtoEventId(Event event);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
