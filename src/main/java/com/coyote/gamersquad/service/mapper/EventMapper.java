package com.coyote.gamersquad.service.mapper;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.EventDTO;
import com.coyote.gamersquad.service.dto.GameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "appUserId")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameTitle")
    EventDTO toDto(Event s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("gameTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    GameDTO toDtoGameTitle(Game game);
}
