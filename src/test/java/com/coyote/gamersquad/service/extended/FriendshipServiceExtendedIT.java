package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.dto.projection.PlayerFriendshipDTO;
import com.coyote.gamersquad.repository.extended.FriendshipChatRepositoryExtended;
import com.coyote.gamersquad.service.dto.AppUserDTO;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
import com.coyote.gamersquad.service.errors.ForbiddenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for {@link FriendshipServiceExtended}.
 */
@IntegrationTest
@Transactional
class FriendshipServiceExtendedIT {

    @Autowired
    private FriendshipServiceExtended underTest;

    @Autowired
    private FriendshipChatRepositoryExtended friendshipChatRepository;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void getAllFriends() {
        // Given
        String userLogin = "daniel";

        // When
        List<AppUserDTO> results = underTest.getAllFriends(userLogin);

        // Then
        assertThat(results).hasSize(6);
    }

    @Test
    @Transactional
    void getAllPlayersFriends() {
        // Given
        String userLogin = "daniel";

        // When
        List<PlayerFriendshipDTO> results = underTest.getAllPlayersFriends(userLogin);

        // Then
        assertThat(results).hasSize(6);
    }

    @Test
    @Transactional
    void getPlayerFriendByFriendshipId_returnPlayerFriendshipDTO() {
        // Given
        Long friendshipId = 4L;
        String userLogin = "daniel";
        String friendLogin = "bruno";

        // When
        PlayerFriendshipDTO result = underTest.getPlayerFriendByFriendshipId(friendshipId, userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserLogin()).isEqualTo(friendLogin);
    }

    @Test
    @Transactional
    void getPlayerFriendByFriendshipId_throwForbiddenException() {
        // Given
        Long friendshipId = 6L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.getPlayerFriendByFriendshipId(friendshipId, userLogin))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void createFriendshipDemand_returnFriendshipDTO() {
        // Given
        String ownerUserLogin = "daniel";
        Long ownerAppUserId = 14L;
        Long receiverAppUserId = 11L;

        // When
        FriendshipDTO result = underTest.createFriendshipDemand(receiverAppUserId, ownerUserLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIsAccepted()).isFalse();
        assertThat(result.getAppUserOwner().getId()).isEqualTo(ownerAppUserId);
        assertThat(result.getAppUserReceiver().getId()).isEqualTo(receiverAppUserId);
    }

    @Test
    @Transactional
    void createFriendshipDemand_throwForbiddenException_whenAlreadyExist() {
        // Given
        String ownerUserLogin = "daniel";
        Long receiverAppUserId = 12L;

        // Then
        assertThatThrownBy(() -> underTest.createFriendshipDemand(receiverAppUserId, ownerUserLogin))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void acceptFriendshipDemand_returnFriendshipDTO() {
        // Given
        String receiverUserLogin = "daniel";
        Long receiverAppUserId = 14L;
        Long ownerAppUserId = 13L;

        // When
        FriendshipDTO result = underTest.acceptFriendshipDemand(ownerAppUserId, receiverUserLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIsAccepted()).isTrue();
        assertThat(result.getAppUserOwner().getId()).isEqualTo(ownerAppUserId);
        assertThat(result.getAppUserReceiver().getId()).isEqualTo(receiverAppUserId);
    }

    @Test
    @Transactional
    void acceptFriendshipDemand_throwForbiddenException_whenAlreadyAccepted() {
        // Given
        String receiverUserLogin = "daniel";
        Long ownerAppUserId = 12L;

        // Then
        assertThatThrownBy(() -> underTest.acceptFriendshipDemand(ownerAppUserId, receiverUserLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("already accepted");
    }

    @Test
    @Transactional
    void deleteFriendship_shouldDeleteFriendshipAndFriendshipChats() {
        // Given
        String userLogin = "daniel";
        Long appUserId = 18L;
        Long friendshipId = 9L;
        int previousNbrOfFriends = underTest.getAllFriends(userLogin).size();

        // When
        underTest.deleteFriendship(appUserId, userLogin);

        // Then
        assertThat(underTest.getAllFriends(userLogin)).hasSize(previousNbrOfFriends - 1);
        assertThat(friendshipChatRepository.getAllPlayerChatsByFriendshipId(friendshipId)).isEmpty();
    }
}
