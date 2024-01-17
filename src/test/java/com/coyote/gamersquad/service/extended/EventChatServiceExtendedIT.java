package com.coyote.gamersquad.service.extended;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.IntegrationTest;
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
    void getAllEventPlayerChatsByEventId() {}

    @Test
    @Transactional
    void createEventChatMessage() {}
}
