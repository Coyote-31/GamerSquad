package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.domain.GameSub;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.GameRepositoryExtended;
import com.coyote.gamersquad.repository.extended.GameSubRepositoryExtended;
import com.coyote.gamersquad.service.GameSubService;
import com.coyote.gamersquad.service.dto.GameSubDTO;
import com.coyote.gamersquad.service.mapper.GameSubMapper;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation extended for managing {@link GameSub}.
 */
@Service
@Transactional
public class GameSubServiceExtended extends GameSubService {

    private final Logger log = LoggerFactory.getLogger(GameSubServiceExtended.class);

    private final GameSubRepositoryExtended gameSubRepository;

    private final GameSubMapper gameSubMapper;

    private final AppUserRepositoryExtended appUserRepository;

    private final GameRepositoryExtended gameRepository;

    public GameSubServiceExtended(
        GameSubRepositoryExtended gameSubRepository,
        GameSubMapper gameSubMapper,
        AppUserRepositoryExtended appUserRepository,
        GameRepositoryExtended gameRepository
    ) {
        super(gameSubRepository, gameSubMapper);
        this.gameSubRepository = gameSubRepository;
        this.gameSubMapper = gameSubMapper;
        this.appUserRepository = appUserRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Is a User subscribed to a Game.
     *
     * @param userLogin the login of the user.
     * @param gameId the id of the game.
     * @return the persisted entity.
     */
    public boolean isSubscribed(String userLogin, Long gameId) {
        log.debug("Request to isSubscribed User : {} to Game : {}", userLogin, gameId);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found for id : " + gameId));

        return gameSubRepository.existsByAppUserAndGame(appUser, game);
    }

    /**
     * Subscribes a User to a Game.
     *
     * @param userLogin the login of the user.
     * @param gameId the id of the game.
     * @return the persisted entity.
     */
    public GameSubDTO subscribe(String userLogin, Long gameId) {
        log.debug("Request to subscribe User : {} to Game : {}", userLogin, gameId);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found for id : " + gameId));

        if (gameSubRepository.existsByAppUserAndGame(appUser, game)) {
            throw new EntityExistsException("User : " + userLogin + " is already subscribed to Game : " + gameId);
        }

        GameSub gameSub = new GameSub();
        gameSub.setAppUser(appUser);
        gameSub.setGame(game);

        GameSub gameSubSaved = gameSubRepository.save(gameSub);

        return gameSubMapper.toDto(gameSubSaved);
    }

    public void unsubscribe(String userLogin, Long gameId) {
        log.debug("Request to unsubscribe User : {} from Game : {}", userLogin, gameId);

        AppUser appUser = appUserRepository
            .getAppUserByInternalUser_Login(userLogin)
            .orElseThrow(() -> new EntityNotFoundException("AppUser not found for login : " + userLogin));
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found for id : " + gameId));

        GameSub gameSub = gameSubRepository
            .findOneByAppUserAndGame(appUser, game)
            .orElseThrow(() -> new EntityNotFoundException("User : " + userLogin + " is not subscribed to Game : " + gameId));

        gameSubRepository.delete(gameSub);
    }
}
