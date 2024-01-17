package com.coyote.gamersquad.service.extended;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    void getAllEventPlayersByEventId() {}

    @Test
    @Transactional
    void isAlreadySubscribedByEventId() {}

    @Test
    @Transactional
    void isAcceptedSubscriber() {}

    @Test
    @Transactional
    void isAlreadyAcceptedByEventId() {}

    @Test
    @Transactional
    void getAllFriendsForInviteByEventId() {}

    @Test
    @Transactional
    void subscribeUserByEventId() {}

    @Test
    @Transactional
    void unsubscribeUserByEventId() {}

    @Test
    @Transactional
    void inviteUserByEventIdAndAppUserId() {}

    @Test
    @Transactional
    void acceptInviteByEventIdAndUserLogin() {}

    @Test
    @Transactional
    void refuseInviteByEventIdAndUserLogin() {}

    @Test
    @Transactional
    void deleteEventSubFromOwner() {}
}
