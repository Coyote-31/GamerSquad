package com.coyote.gamersquad.service.extended;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.dto.projection.EventPlayerChatDTO;
import com.coyote.gamersquad.service.dto.EventChatDTO;
import com.coyote.gamersquad.service.dto.form.EventMessageDTO;
import com.coyote.gamersquad.service.errors.ForbiddenException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link EventChatServiceExtended}.
 */
@IntegrationTest
@Transactional
class EventChatServiceExtendedIT {

    @Autowired
    private EventChatServiceExtended underTest;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void getAllEventPlayerChatsByEventId_returnList_whenUserIsOwner() {
        // Given
        Long eventId = 7L;
        String userLogin = "daniel";

        // When
        List<EventPlayerChatDTO> results = underTest.getAllEventPlayerChatsByEventId(eventId, userLogin);

        // Then
        assertThat(results).hasSize(4);
    }

    @Test
    @Transactional
    void getAllEventPlayerChatsByEventId_returnList_whenUserIsAccepted() {
        // Given
        Long eventId = 7L;
        String userLogin = "bruno";

        // When
        List<EventPlayerChatDTO> results = underTest.getAllEventPlayerChatsByEventId(eventId, userLogin);

        // Then
        assertThat(results).hasSize(4);
    }

    @Test
    @Transactional
    void getAllEventPlayerChatsByEventId_throwForbiddenException_whenUserNotOwnerAndNotAccepted() {
        // Given
        Long eventId = 7L;
        String userLogin = "anne";

        // Then
        assertThatThrownBy(() -> underTest.getAllEventPlayerChatsByEventId(eventId, userLogin)).isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void createEventChatMessage_returnEventChatDTO_whenUserIsOwner() {
        // Given
        Long eventId = 7L;
        String userLogin = "daniel";
        String message = "message";
        EventMessageDTO eventMessage = new EventMessageDTO();
        eventMessage.setMessage(message);

        // When
        EventChatDTO result = underTest.createEventChatMessage(eventMessage, eventId, userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(message);
    }

    @Test
    @Transactional
    void createEventChatMessage_returnEventChatDTO_whenUserIsAccepted() {
        // Given
        Long eventId = 7L;
        String userLogin = "bruno";
        String message = "message";
        EventMessageDTO eventMessage = new EventMessageDTO();
        eventMessage.setMessage(message);

        // When
        EventChatDTO result = underTest.createEventChatMessage(eventMessage, eventId, userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(message);
    }

    @Test
    @Transactional
    void createEventChatMessage_throwForbiddenException_whenUserNotOwnerAndNotAccepted() {
        // Given
        Long eventId = 7L;
        String userLogin = "anne";
        String message = "message";
        EventMessageDTO eventMessage = new EventMessageDTO();
        eventMessage.setMessage(message);

        // Then
        assertThatThrownBy(() -> underTest.createEventChatMessage(eventMessage, eventId, userLogin)).isInstanceOf(ForbiddenException.class);
    }
}
