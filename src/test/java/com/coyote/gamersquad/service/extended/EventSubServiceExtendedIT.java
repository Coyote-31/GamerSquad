package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.dto.projection.EventFriendDTO;
import com.coyote.gamersquad.domain.dto.projection.EventPlayerDTO;
import com.coyote.gamersquad.service.dto.EventSubDTO;
import com.coyote.gamersquad.service.errors.AppUserNotFoundException;
import com.coyote.gamersquad.service.errors.EventSubNotFoundException;
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
 * Integration tests for {@link EventSubServiceExtended}.
 */
@IntegrationTest
@Transactional
class EventSubServiceExtendedIT {

    @Autowired
    private EventSubServiceExtended underTest;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void getAllEventPlayersByEventId_returnEventPlayerDTOList() {
        // Given
        Long eventId = 7L;
        String userLogin = "daniel";

        // When
        List<EventPlayerDTO> results = underTest.getAllEventPlayersByEventId(eventId, userLogin);

        // Then
        assertThat(results).hasSize(3);
    }

    @Test
    @Transactional
    void getAllEventPlayersByEventId_throwForbiddenException_whenEventIsPrivateAndUserNotOwnerOrNotAccepted() {
        // Given
        Long eventId = 8L;
        String userLogin = "anne";

        // Then
        assertThatThrownBy(() -> underTest.getAllEventPlayersByEventId(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void isAlreadySubscribedByEventId_returnTrue() {
        // Given
        Long eventId = 4L;
        String userLogin = "daniel";

        // When
        boolean result = underTest.isAlreadySubscribedByEventId(eventId, userLogin);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @Transactional
    void isAlreadySubscribedByEventId_returnFalse() {
        // Given
        Long eventId = 4L;
        String userLogin = "anne";

        // When
        boolean result = underTest.isAlreadySubscribedByEventId(eventId, userLogin);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @Transactional
    void isAcceptedSubscriber_returnTrue() {
        // Given
        Long appUserId = 14L;
        AppUser appUser = new AppUser();
        appUser.setId(appUserId);

        Long eventId = 5L;
        Event event = new Event();
        event.setId(eventId);

        // When
        boolean result = underTest.isAcceptedSubscriber(appUser, event);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @Transactional
    void isAcceptedSubscriber_returnFalse() {
        // Given
        Long appUserId = 14L;
        AppUser appUser = new AppUser();
        appUser.setId(appUserId);

        Long eventId = 4L;
        Event event = new Event();
        event.setId(eventId);

        // When
        boolean result = underTest.isAcceptedSubscriber(appUser, event);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @Transactional
    void isAlreadyAcceptedByEventId_returnTrue() {
        // Given
        Long eventId = 5L;
        String userLogin = "daniel";

        // When
        boolean result = underTest.isAlreadyAcceptedByEventId(eventId, userLogin);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @Transactional
    void isAlreadyAcceptedByEventId_returnFalse() {
        // Given
        Long eventId = 4L;
        String userLogin = "daniel";

        // When
        boolean result = underTest.isAlreadyAcceptedByEventId(eventId, userLogin);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @Transactional
    void getAllFriendsForInviteByEventId_returnEventFriendDTOList() {
        // Given
        Long eventId = 6L;
        String userLogin = "charles";

        // When
        List<EventFriendDTO> results = underTest.getAllFriendsForInviteByEventId(eventId, userLogin);

        // Then
        assertThat(results).hasSize(1);
    }

    @Test
    @Transactional
    void getAllFriendsForInviteByEventId_throwForbiddenException() {
        // Given
        Long eventId = 7L;
        String userLogin = "charles";

        // Then
        assertThatThrownBy(() -> underTest.getAllFriendsForInviteByEventId(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void subscribeUserByEventId_returnEventSubDTO() {
        // Given
        Long eventId = 3L;
        String userLogin = "daniel";
        Long appUserId = 14L;

        // When
        EventSubDTO result = underTest.subscribeUserByEventId(eventId, userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIsAccepted()).isTrue();
        assertThat(result.getEvent().getId()).isEqualTo(eventId);
        assertThat(result.getAppUser().getId()).isEqualTo(appUserId);
    }

    @Test
    @Transactional
    void subscribeUserByEventId_throwForbiddenException_whenUserIsOwner() {
        // Given
        Long eventId = 7L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.subscribeUserByEventId(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("own event");
    }

    @Test
    @Transactional
    void subscribeUserByEventId_throwForbiddenException_whenEventIsPrivate() {
        // Given
        Long eventId = 5L;
        String userLogin = "anne";

        // Then
        assertThatThrownBy(() -> underTest.subscribeUserByEventId(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("private event");
    }

    @Test
    @Transactional
    void subscribeUserByEventId_throwForbiddenException_whenUserAlreadySubscribed() {
        // Given
        Long eventId = 4L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.subscribeUserByEventId(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("already subscribed");
    }

    @Test
    @Transactional
    void unsubscribeUserByEventId_shouldDeleteEventSub() {
        // Given
        Long eventId = 4L;
        String userLogin = "daniel";
        assertThat(underTest.isAlreadySubscribedByEventId(eventId, userLogin)).isTrue();

        // When
        underTest.unsubscribeUserByEventId(eventId, userLogin);

        // Then
        assertThat(underTest.isAlreadySubscribedByEventId(eventId, userLogin)).isFalse();
    }

    @Test
    @Transactional
    void unsubscribeUserByEventId_throwForbiddenException_whenUserIsNotSubscribed() {
        // Given
        Long eventId = 6L;
        String userLogin = "daniel";
        assertThat(underTest.isAlreadySubscribedByEventId(eventId, userLogin)).isFalse();

        // Then
        assertThatThrownBy(() -> underTest.unsubscribeUserByEventId(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("not yet subscribed");
    }

    @Test
    @Transactional
    void inviteUserByEventIdAndAppUserId_returnEventSubDTO() {
        // Given
        Long eventId = 6L;
        Long receiverAppUserId = 15L;
        String senderUserLogin = "charles";

        // When
        EventSubDTO result = underTest.inviteUserByEventIdAndAppUserId(eventId, receiverAppUserId, senderUserLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEvent().getId()).isEqualTo(eventId);
        assertThat(result.getAppUser().getId()).isEqualTo(receiverAppUserId);
        assertThat(result.getIsAccepted()).isFalse();
    }

    @Test
    @Transactional
    void inviteUserByEventIdAndAppUserId_throwForbiddenException_whenSenderIsNotOwner() {
        // Given
        Long eventId = 3L;
        Long receiverAppUserId = 15L;
        String senderUserLogin = "charles";

        // Then
        assertThatThrownBy(() -> underTest.inviteUserByEventIdAndAppUserId(eventId, receiverAppUserId, senderUserLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("not owned");
    }

    @Test
    @Transactional
    void inviteUserByEventIdAndAppUserId_throwForbiddenException_whenReceiverIsNotAcceptedFriend() {
        // Given
        Long eventId = 6L;
        Long receiverAppUserId = 12L;
        String senderUserLogin = "charles";

        // Then
        assertThatThrownBy(() -> underTest.inviteUserByEventIdAndAppUserId(eventId, receiverAppUserId, senderUserLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("accepted friend");
    }

    @Test
    @Transactional
    void inviteUserByEventIdAndAppUserId_throwForbiddenException_whenReceiverIsAlreadySubscribed() {
        // Given
        Long eventId = 7L;
        Long receiverAppUserId = 18L;
        String senderUserLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.inviteUserByEventIdAndAppUserId(eventId, receiverAppUserId, senderUserLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("already subscribed");
    }

    @Test
    @Transactional
    void acceptInviteByEventIdAndUserLogin_returnEventSubDTO() {
        // Given
        Long eventId = 4L;
        String userLogin = "daniel";
        Long appUserId = 14L;

        // When
        EventSubDTO result = underTest.acceptInviteByEventIdAndUserLogin(eventId, userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getIsAccepted()).isTrue();
        assertThat(result.getEvent().getId()).isEqualTo(eventId);
        assertThat(result.getAppUser().getId()).isEqualTo(appUserId);
    }

    @Test
    @Transactional
    void acceptInviteByEventIdAndUserLogin_throwForbiddenException_whenNoInvitationReceived() {
        // Given
        Long eventId = 6L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.acceptInviteByEventIdAndUserLogin(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("not receive");
    }

    @Test
    @Transactional
    void acceptInviteByEventIdAndUserLogin_throwForbiddenException_whenAlreadyAccepted() {
        // Given
        Long eventId = 5L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.acceptInviteByEventIdAndUserLogin(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("already accepted");
    }

    @Test
    @Transactional
    void refuseInviteByEventIdAndUserLogin_shouldDeleteEventSub() {
        // Given
        Long eventId = 4L;
        String userLogin = "daniel";

        // When
        underTest.refuseInviteByEventIdAndUserLogin(eventId, userLogin);

        // Then
        assertThat(underTest.isAlreadySubscribedByEventId(eventId, userLogin)).isFalse();
    }

    @Test
    @Transactional
    void refuseInviteByEventIdAndUserLogin_throwForbiddenException_whenNoInvitationReceived() {
        // Given
        Long eventId = 6L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.refuseInviteByEventIdAndUserLogin(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("did not receive one");
    }

    @Test
    @Transactional
    void refuseInviteByEventIdAndUserLogin_throwForbiddenException_whenAlreadyAccepted() {
        // Given
        Long eventId = 5L;
        String userLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.refuseInviteByEventIdAndUserLogin(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("already accepted");
    }

    @Test
    @Transactional
    void deleteEventSubFromOwner_shouldDeleteEventSub() {
        // Given
        Long eventId = 7L;
        Long toDeleteAppUserId = 12L;
        String toDeleteUserLogin = "bruno";
        String ownerUserLogin = "daniel";

        // When
        underTest.deleteEventSubFromOwner(toDeleteAppUserId, eventId, ownerUserLogin);

        // Then
        assertThat(underTest.isAlreadySubscribedByEventId(eventId, toDeleteUserLogin)).isFalse();
    }

    @Test
    @Transactional
    void deleteEventSubFromOwner_throwForbiddenException_whenNotOwner() {
        // Given
        Long eventId = 7L;
        Long toDeleteAppUserId = 12L;
        String ownerUserLogin = "anne";

        // Then
        assertThatThrownBy(() -> underTest.deleteEventSubFromOwner(toDeleteAppUserId, eventId, ownerUserLogin))
            .isInstanceOf(ForbiddenException.class)
            .hasMessageContaining("not owned");
    }

    @Test
    @Transactional
    void deleteEventSubFromOwner_throwAppUserNotFoundException_whenAppUserNotExist() {
        // Given
        Long eventId = 7L;
        Long toDeleteAppUserId = 0L;
        String ownerUserLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.deleteEventSubFromOwner(toDeleteAppUserId, eventId, ownerUserLogin))
            .isInstanceOf(AppUserNotFoundException.class);
    }

    @Test
    @Transactional
    void deleteEventSubFromOwner_throwEventSubNotFoundException_whenAppUserNotSubscribed() {
        // Given
        Long eventId = 7L;
        Long toDeleteAppUserId = 11L;
        String ownerUserLogin = "daniel";

        // Then
        assertThatThrownBy(() -> underTest.deleteEventSubFromOwner(toDeleteAppUserId, eventId, ownerUserLogin))
            .isInstanceOf(EventSubNotFoundException.class);
    }
}
