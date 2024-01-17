package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.dto.projection.EventDetailDTO;
import com.coyote.gamersquad.repository.extended.EventChatRepositoryExtended;
import com.coyote.gamersquad.repository.extended.EventSubRepositoryExtended;
import com.coyote.gamersquad.service.dto.form.EventCreateDTO;
import com.coyote.gamersquad.service.errors.EventNotFoundException;
import com.coyote.gamersquad.service.errors.ForbiddenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for {@link EventServiceExtended}.
 */
@IntegrationTest
@Transactional
class EventServiceExtendedIT {

    @Autowired
    private EventServiceExtended underTest;

    @Autowired
    private EventSubRepositoryExtended eventSubRepository;

    @Autowired
    private EventChatRepositoryExtended eventChatRepository;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void getAllEventDetailsPublicByGameId_returnEventDetailList() {
        // Given
        Long gameId = 1L;

        // When
        List<EventDetailDTO> results = underTest.getAllEventDetailsPublicByGameId(gameId);

        // Then
        assertThat(results).hasSize(1);
    }

    @Test
    @Transactional
    void getEventDetailByEventId_returnEventDetailDTO() {
        // Given
        Long eventId = 3L;
        String userLogin = "anne";
        Long gameId = 1L;
        String eventTitle = "Soirée voleur";

        // When
        EventDetailDTO result = underTest.getEventDetailByEventId(eventId, userLogin);

        // Then
        assertThat(result.getId()).isEqualTo(eventId);
        assertThat(result.getGameId()).isEqualTo(gameId);
        assertThat(result.getTitle()).isEqualTo(eventTitle);
    }

    @Test
    @Transactional
    void getEventDetailByEventId_returnEventDetailDTO_whenEventIsPrivateAndUserIsOwner() {
        // Given
        Long eventId = 5L;
        String userLogin = "bruno";
        Long gameId = 1L;
        String eventTitle = "Early access try hard";

        // When
        EventDetailDTO result = underTest.getEventDetailByEventId(eventId, userLogin);

        // Then
        assertThat(result.getId()).isEqualTo(eventId);
        assertThat(result.getGameId()).isEqualTo(gameId);
        assertThat(result.getTitle()).isEqualTo(eventTitle);
    }

    @Test
    @Transactional
    void getEventDetailByEventId_returnEventDetailDTO_whenEventIsPrivateAndUserIsAccepted() {
        // Given
        Long eventId = 5L;
        String userLogin = "daniel";
        Long gameId = 1L;
        String eventTitle = "Early access try hard";

        // When
        EventDetailDTO result = underTest.getEventDetailByEventId(eventId, userLogin);

        // Then
        assertThat(result.getId()).isEqualTo(eventId);
        assertThat(result.getGameId()).isEqualTo(gameId);
        assertThat(result.getTitle()).isEqualTo(eventTitle);
    }

    @Test
    @Transactional
    void getEventDetailByEventId_throwForbiddenException_whenEventIsPrivateAndUserIsNotOwnerAndNotAccepted() {
        // Given
        Long eventId = 5L;
        String userLogin = "charles";

        // Then
        assertThatThrownBy(() -> underTest.getEventDetailByEventId(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void getAllEventDetailsOwnedByUserLogin_returnEventDetailList() {
        // Given
        String userLogin = "daniel";

        // When
        List<EventDetailDTO> results = underTest.getAllEventDetailsOwnedByUserLogin(userLogin);

        // Then
        assertThat(results).hasSize(4);
    }

    @Test
    @Transactional
    void getAllEventDetailsSubscribedByUserLogin_returnEventDetailList() {
        // Given
        String userLogin = "bruno";

        // When
        List<EventDetailDTO> results = underTest.getAllEventDetailsSubscribedByUserLogin(userLogin);

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    @Transactional
    void getAllEventDetailsPendingByUserLogin_returnEventDetailList() {
        // Given
        String userLogin = "bruno";

        // When
        List<EventDetailDTO> results = underTest.getAllEventDetailsPendingByUserLogin(userLogin);

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    @Transactional
    void createEvent_returnEventDetailDTO() {
        // Given
        String title = "Test title";
        String description = "Test description";
        ZonedDateTime meetingDate = ZonedDateTime.now().plusDays(7).withNano(777000000);
        boolean privacy = false;

        EventCreateDTO eventForm = new EventCreateDTO();
        eventForm.setTitle(title);
        eventForm.setDescription(description);
        eventForm.setMeetingDate(meetingDate);
        eventForm.setIsPrivate(privacy);
        Long gameId = 1L;
        String userLogin = "bruno";

        // When
        EventDetailDTO result = underTest.createEvent(eventForm, gameId, userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getMeetingDate()).isEqualTo(meetingDate);
        assertThat(result.isPrivate()).isEqualTo(privacy);
        assertThat(result.getGameId()).isEqualTo(gameId);
        assertThat(result.getOwnerLogin()).isEqualTo(userLogin);
    }

    @Test
    @Transactional
    void updateEvent_returnEventDetailDTO() {
        // Given
        String title = "Test title";
        String description = "Test description";
        ZonedDateTime meetingDate = ZonedDateTime.now().plusDays(7).withNano(777000000);
        boolean privacy = false;

        EventCreateDTO eventForm = new EventCreateDTO();
        eventForm.setTitle(title);
        eventForm.setDescription(description);
        eventForm.setMeetingDate(meetingDate);
        eventForm.setIsPrivate(privacy);
        Long gameId = 1L;
        String userLogin = "bruno";

        EventDetailDTO eventDetailToUpdate = underTest.createEvent(eventForm, gameId, userLogin);
        assertThat(eventDetailToUpdate).isNotNull();

        String titleUpdated = "Test title updated";
        String descriptionUpdated = "Test description updated";
        ZonedDateTime meetingDateUpdated = ZonedDateTime.now().plusDays(8).withNano(888000000);
        boolean privacyUpdated = true;

        eventForm.setTitle(titleUpdated);
        eventForm.setDescription(descriptionUpdated);
        eventForm.setMeetingDate(meetingDateUpdated);
        eventForm.setIsPrivate(privacyUpdated);

        // When
        EventDetailDTO result = underTest.updateEvent(eventForm, eventDetailToUpdate.getId(), userLogin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(titleUpdated);
        assertThat(result.getDescription()).isEqualTo(descriptionUpdated);
        assertThat(result.getMeetingDate()).isEqualTo(meetingDateUpdated);
        assertThat(result.isPrivate()).isEqualTo(privacyUpdated);
        assertThat(result.getGameId()).isEqualTo(gameId);
        assertThat(result.getOwnerLogin()).isEqualTo(userLogin);
    }

    @Test
    @Transactional
    void updateEvent_throwForbiddenException_whenUserIsNotOwner() {
        // Given
        String title = "Test title";
        String description = "Test description";
        ZonedDateTime meetingDate = ZonedDateTime.now().plusDays(7).withNano(777000000);
        boolean privacy = false;

        EventCreateDTO eventForm = new EventCreateDTO();
        eventForm.setTitle(title);
        eventForm.setDescription(description);
        eventForm.setMeetingDate(meetingDate);
        eventForm.setIsPrivate(privacy);
        Long gameId = 1L;
        String userLogin = "bruno";
        String userLoginNotOwner = "anne";

        EventDetailDTO eventDetailToUpdate = underTest.createEvent(eventForm, gameId, userLogin);
        assertThat(eventDetailToUpdate).isNotNull();

        String titleUpdated = "Test title updated";
        String descriptionUpdated = "Test description updated";
        ZonedDateTime meetingDateUpdated = ZonedDateTime.now().plusDays(8).withNano(888000000);
        boolean privacyUpdated = true;

        eventForm.setTitle(titleUpdated);
        eventForm.setDescription(descriptionUpdated);
        eventForm.setMeetingDate(meetingDateUpdated);
        eventForm.setIsPrivate(privacyUpdated);

        // Then
        assertThatThrownBy(() -> underTest.updateEvent(eventForm, eventDetailToUpdate.getId(), userLoginNotOwner))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @Transactional
    void deleteEventByIdFromOwner_ShouldDeleteEventChatAndEventSubAndEvent() {
        // Given
        Long eventId = 7L;
        Event eventToDelete = new Event();
        eventToDelete.setId(eventId);
        String userLogin = "daniel";

        assertThat(underTest.getEventDetailByEventId(eventId, userLogin)).isNotNull();
        assertThat(eventSubRepository.getAllEventPlayersByEvent(eventToDelete)).isNotEmpty();
        assertThat(eventChatRepository.getAllEventPlayerChatsByEvent(eventToDelete)).isNotEmpty();

        // When
        underTest.deleteEventByIdFromOwner(eventId, userLogin);

        // Then
        assertThatThrownBy(() -> underTest.getEventDetailByEventId(eventId, userLogin))
            .isInstanceOf(EventNotFoundException.class);
        assertThat(eventSubRepository.getAllEventPlayersByEvent(eventToDelete)).isEmpty();
        assertThat(eventChatRepository.getAllEventPlayerChatsByEvent(eventToDelete)).isEmpty();
    }

    @Test
    @Transactional
    void deleteEventByIdFromOwner_throwForbiddenException() {
        // Given
        Long eventId = 7L;
        String userLogin = "bruno";

        // Then
        assertThatThrownBy(() -> underTest.deleteEventByIdFromOwner(eventId, userLogin))
            .isInstanceOf(ForbiddenException.class);
    }
}
