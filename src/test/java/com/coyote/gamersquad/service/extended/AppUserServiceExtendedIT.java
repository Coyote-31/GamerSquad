package com.coyote.gamersquad.service.extended;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.User;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.UserRepositoryExtended;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link AppUserServiceExtended}.
 */
@IntegrationTest
@Transactional
class AppUserServiceExtendedIT {

    @Autowired
    private AppUserServiceExtended underTest;

    @Autowired
    private AppUserRepositoryExtended appUserRepository;

    @Autowired
    private UserRepositoryExtended userRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    @MockBean
    private DateTimeProvider dateTimeProvider;

    private static final String DEFAULT_LOGIN = "johndoe";
    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String DEFAULT_FIRSTNAME = "john";
    private static final String DEFAULT_LASTNAME = "doe";
    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
    private static final String DEFAULT_LANGKEY = "dummy";

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(LocalDateTime.now()));
        auditingHandler.setDateTimeProvider(dateTimeProvider);
    }

    @AfterEach
    void tearDown() {}

    @Test
    @Transactional
    void registerAppUser() {
        // Given
        User userToRegister = userRepository.saveAndFlush(user);

        // When
        underTest.registerAppUser(userToRegister);

        // Then
        assertThat(appUserRepository.getAppUserByInternalUser_Login(DEFAULT_LOGIN)).isNotEmpty();
    }

    @Test
    @Transactional
    void delete() {
        // Given
        User userToRegister = userRepository.saveAndFlush(user);
        underTest.registerAppUser(userToRegister);

        // When
        underTest.delete(user.getLogin());

        // Then
        assertThat(appUserRepository.getAppUserByInternalUser_Login(user.getLogin())).isEmpty();
    }
}
