package com.coyote.gamersquad.service.mapper;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Friendship} and its DTO {@link FriendshipDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface FriendshipMapper extends EntityMapper<FriendshipDTO, Friendship> {
    @Mapping(target = "appUserOwner", source = "appUserOwner", qualifiedByName = "appUserId")
    @Mapping(target = "appUserReceiver", source = "appUserReceiver", qualifiedByName = "appUserId")
    FriendshipDTO toDto(Friendship s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
