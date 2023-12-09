package com.coyote.gamersquad.repository.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.repository.AppUserRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository extended for the AppUser entity.
 */
@Repository
public interface AppUserRepositoryExtended extends AppUserRepository {
    void deleteByInternalUser_Login(@NotNull String internalUser_login);

    Optional<AppUser> getAppUserByInternalUser_Login(String internalUser_login);

    @Query(
        "select appUser from AppUser appUser " +
        "join GameSub gameSub on appUser = gameSub.appUser " +
        "where gameSub.game.id = :gameId " +
        "and appUser.internalUser.login != :userLogin"
    )
    List<AppUser> findAllAppUsersSubToGame(@Param("userLogin") String userLogin, @Param("gameId") Long gameId);

    @Query(
        "from AppUser appUser " +
        "join Friendship fs on fs.appUserOwner = appUser or fs.appUserReceiver = appUser " +
        "where (fs.appUserOwner.id = :appUserId " +
        "or fs.appUserReceiver.id = :appUserId) " +
        "and appUser.id != :appUserId"
    )
    List<AppUser> findAllFriends(@Param("appUserId") Long appUserId);
}
