package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.dto.projection.PlayerChatDTO;
import com.coyote.gamersquad.service.dto.FriendshipChatDTO;
import com.coyote.gamersquad.service.dto.form.FriendMessageDTO;
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
 * Integration tests for {@link FriendshipChatServiceExtended}.
 */
@IntegrationTest
@Transactional
class FriendshipChatServiceExtendedIT {

    @Autowired
    private FriendshipChatServiceExtended underTest;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void getAllPlayerChatsByFriendshipId_returnPlayerChatDTOList() {
        // Given
        Long friendshipId = 9L;
        String userLogin = "daniel";

        // When
        List<PlayerChatDTO> results = underTest.getAllPlayerChatsByFriendshipId(friendshipId, userLogin);

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    @Transactional
    void getAllPlayerChatsByFriendshipId_throwForbiddenException() {
        // Given
        Long friendshipId = 1L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.getAllPlayerChatsByFriendshipId(friendshipId, userLogin))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void createFriendshipChatMessage_returnFriendshipChatDTO() {
        // Given
        Long friendshipId = 9L;
        String userLogin = "daniel";
        Long appUserId = 14L;
        String message = "message";
        FriendMessageDTO friendMessage = new FriendMessageDTO();
        friendMessage.setMessage(message);

        // When
        FriendshipChatDTO result = underTest.createFriendshipChatMessage(friendMessage, friendshipId, userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFriendship().getId()).isEqualTo(friendshipId);
        assertThat(result.getSender().getId()).isEqualTo(appUserId);
        assertThat(result.getMessage()).isEqualTo(message);
    }

    @Test
    @Transactional
    void createFriendshipChatMessage_throwForbiddenException_whenNotPartOfFriendship() {
        // Given
        Long friendshipId = 6L;
        String userLogin = "daniel";
        FriendMessageDTO friendMessage = new FriendMessageDTO();

        // Then
        assertThatThrownBy(() -> underTest.createFriendshipChatMessage(friendMessage, friendshipId, userLogin))
                .isInstanceOf(ForbiddenException.class)
                .hasMessageContaining("not part of");
    }

    @Test
    @Transactional
    void createFriendshipChatMessage_throwForbiddenException_whenFriendshipIsNotActivated() {
        // Given
        Long friendshipId = 5L;
        String userLogin = "daniel";
        FriendMessageDTO friendMessage = new FriendMessageDTO();

        // Then
        assertThatThrownBy(() -> underTest.createFriendshipChatMessage(friendMessage, friendshipId, userLogin))
                .isInstanceOf(ForbiddenException.class)
                .hasMessageContaining("is not activated");
    }
}
