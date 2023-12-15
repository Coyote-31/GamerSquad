package com.coyote.gamersquad.service.mapper;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.domain.FriendshipChat;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.FriendshipChatDTO;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FriendshipChat} and its DTO {@link FriendshipChatDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface FriendshipChatMapper extends EntityMapper<FriendshipChatDTO, FriendshipChat> {
    @Mapping(target = "friendship", source = "friendship", qualifiedByName = "friendshipId")
    @Mapping(target = "sender", source = "sender", qualifiedByName = "appUserId")
    FriendshipChatDTO toDto(FriendshipChat s);

    @Named("friendshipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FriendshipDTO toDtoFriendshipId(Friendship friendship);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
