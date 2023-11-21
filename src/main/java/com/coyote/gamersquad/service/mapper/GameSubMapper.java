package com.coyote.gamersquad.service.mapper;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.domain.GameSub;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.GameDTO;
import com.coyote.gamersquad.service.dto.GameSubDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameSub} and its DTO {@link GameSubDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface GameSubMapper extends EntityMapper<GameSubDTO, GameSub> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameTitle")
    GameSubDTO toDto(GameSub s);

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
