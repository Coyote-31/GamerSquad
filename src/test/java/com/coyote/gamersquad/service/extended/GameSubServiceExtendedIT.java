package com.coyote.gamersquad.service.extended;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.dto.projection.PlayerFriendshipDTO;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.GameSubDTO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Transactional
class GameSubServiceExtendedIT {

    @Autowired
    private GameSubServiceExtended underTest;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void isSubscribed_returnTrue() {
        // Given
        String userLogin = "daniel";
        Long gameId = 1L;

        // When
        boolean result = underTest.isSubscribed(userLogin, gameId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @Transactional
    void isSubscribed_returnFalse() {
        // Given
        String userLogin = "anne";
        Long gameId = 1L;

        // When
        boolean result = underTest.isSubscribed(userLogin, gameId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @Transactional
    void subscribe_returnValidGameSubDTO() {
        // Given
        String userLogin = "anne";
        Long gameId = 1L;

        // When
        GameSubDTO result = underTest.subscribe(userLogin, gameId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getAppUser().getId()).isEqualTo(11L);
        assertThat(result.getGame().getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    void unsubscribe_shouldDeleteGameSub() {
        // Given
        String userLogin = "daniel";
        Long gameId = 1L;

        // When
        underTest.unsubscribe(userLogin, gameId);

        // Then
        assertThat(underTest.isSubscribed(userLogin, gameId)).isFalse();
    }

    @Test
    @Transactional
    void getAllAppUsersSubToGame() {
        // Given
        String userLogin = "daniel";
        Long gameId = 1L;

        // When
        List<AppUserDTO> results = underTest.getAllAppUsersSubToGame(userLogin, gameId);

        // Then
        assertThat(results).hasSize(6);
    }

    @Test
    @Transactional
    void getAllPlayersSubToGame() {
        // Given
        String userLogin = "daniel";
        Long gameId = 1L;

        // When
        List<PlayerFriendshipDTO> results = underTest.getAllPlayersSubToGame(userLogin, gameId);

        // Then
        assertThat(results).hasSize(6);
    }
}
