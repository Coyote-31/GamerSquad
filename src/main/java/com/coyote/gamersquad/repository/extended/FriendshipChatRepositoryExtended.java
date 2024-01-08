package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.repository.FriendshipChatRepository;
import com.coyote.gamersquad.service.dto.projection.PlayerChatDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the FriendshipChat entity.
 */
@Repository
public interface FriendshipChatRepositoryExtended extends FriendshipChatRepository {
    @Query(
        "select new com.coyote.gamersquad.service.dto.projection.PlayerChatDTO(" +
        "fc.sender.internalUser.id, " +
        "fc.sender.internalUser.login, " +
        "fc.sender.internalUser.imageUrl, " +
        "fc.sender.id, " +
        "fc.friendship.id, " +
        "fc.id, " +
        "fc.message, " +
        "fc.sendAt" +
        ") " +
        "from FriendshipChat fc " +
        "where fc.friendship.id = :friendshipId " +
        "order by fc.sendAt"
    )
    List<PlayerChatDTO> getAllPlayerChatsByFriendshipId(@Param("friendshipId") Long friendshipId);

    void deleteAllByFriendship_Id(Long friendShipId);
}
