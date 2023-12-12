package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.repository.FriendshipRepository;
import com.coyote.gamersquad.service.dto.projection.PlayerFriendshipDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the Friendship entity.
 */
@Repository
public interface FriendshipRepositoryExtended extends FriendshipRepository {
    @Query(
        "select new com.coyote.gamersquad.service.dto.projection.PlayerFriendshipDTO(" +
        "appUser.internalUser.id, " +
        "appUser.internalUser.login, " +
        "appUser.internalUser.imageUrl, " +
        "appUser.id, " +
        "fs.id, " +
        "fs.isAccepted, " +
        "(fs.appUserOwner = appUser), " +
        "(fs.appUserReceiver = appUser)" +
        ") " +
        "from AppUser appUser " +
        "join Friendship fs on fs.appUserOwner = appUser or fs.appUserReceiver = appUser " +
        "where (fs.appUserOwner.id = :appUserId " +
        "or fs.appUserReceiver.id = :appUserId) " +
        "and appUser.id != :appUserId " +
        "order by appUser.internalUser.login"
    )
    List<PlayerFriendshipDTO> getAllPlayersFriends(@Param("appUserId") Long appUserId);

    @Query(
        "select new com.coyote.gamersquad.service.dto.projection.PlayerFriendshipDTO(" +
        "appUser.internalUser.id, " +
        "appUser.internalUser.login, " +
        "appUser.internalUser.imageUrl, " +
        "appUser.id, " +
        "fs.id, " +
        "fs.isAccepted, " +
        "(fs.appUserOwner.id = appUser.id), " +
        "(fs.appUserReceiver.id = appUser.id)" +
        ") " +
        "from AppUser appUser " +
        "join GameSub gameSub on (gameSub.appUser = appUser " +
        "and gameSub.game.id = :gameId) " +
        "left join Friendship fs on (fs.appUserOwner = appUser or fs.appUserReceiver = appUser) " +
        "and (fs.appUserOwner.id = :appUserId " +
        "or fs.appUserReceiver.id = :appUserId) " +
        "where appUser.id != :appUserId " +
        "order by appUser.internalUser.login"
    )
    List<PlayerFriendshipDTO> getAllPlayersSubToGame(@Param("appUserId") Long appUserId, @Param("gameId") Long gameId);
}
