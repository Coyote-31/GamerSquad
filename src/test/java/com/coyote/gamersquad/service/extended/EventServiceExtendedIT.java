package com.coyote.gamersquad.service.extended;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link EventServiceExtended}.
 */
@IntegrationTest
@Transactional
class EventServiceExtendedIT {

    @Autowired
    private EventServiceExtended underTest;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void getAllEventDetailsPublicByGameId() {}

    @Test
    @Transactional
    void getEventDetailByEventId() {}

    @Test
    @Transactional
    void getAllEventDetailsOwnedByUserLogin() {}

    @Test
    @Transactional
    void getAllEventDetailsSubscribedByUserLogin() {}

    @Test
    @Transactional
    void getAllEventDetailsPendingByUserLogin() {}

    @Test
    @Transactional
    void createEvent() {}

    @Test
    @Transactional
    void updateEvent() {}

    @Test
    @Transactional
    void deleteEventByIdFromOwner() {}
}
