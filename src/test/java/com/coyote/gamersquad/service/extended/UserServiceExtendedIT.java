package com.coyote.gamersquad.service.extended;

import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.Authority;
import com.coyote.gamersquad.domain.User;
import com.coyote.gamersquad.repository.extended.AppUserRepositoryExtended;
import com.coyote.gamersquad.repository.extended.UserRepositoryExtended;
import com.coyote.gamersquad.security.AuthoritiesConstants;
import com.coyote.gamersquad.service.dto.AdminUserDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Integration tests for {@link UserServiceExtended}.
 */
@IntegrationTest
@Transactional
class UserServiceExtendedIT {

    private static final String DEFAULT_LOGIN = "johndoe";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";


    @Autowired
    private UserServiceExtended underTest;

    @Autowired
    private UserRepositoryExtended userRepository;

    @Autowired
    private AppUserRepositoryExtended appUserRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    @MockBean
    private DateTimeProvider dateTimeProvider;

    private User user;

    private AdminUserDTO userDTO;

    @BeforeEach
    public void init() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(false);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);

        userDTO = new AdminUserDTO(user);

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(LocalDateTime.now()));
        auditingHandler.setDateTimeProvider(dateTimeProvider);
    }

    @Test
    @Transactional
    void registerUser_returnUser() {
        // Given
        Authority authorityUser = new Authority();
        authorityUser.setName(AuthoritiesConstants.USER);

        // When
        User result = underTest.registerUser(userDTO, user.getPassword());

        // Then
        assertThat(result.getLogin()).isEqualTo(userDTO.getLogin());
        assertThat(result.isActivated()).isEqualTo(false);
        assertThat(result.getActivationKey()).isNotNull().isNotEmpty();
        assertThat(result.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(result.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(result.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(result.getImageUrl()).isEqualTo(userDTO.getImageUrl());
        assertThat(result.getLangKey()).isEqualTo(userDTO.getLangKey());
        assertThat(result.getAuthorities()).containsExactly(authorityUser);
        assertThat(appUserRepository.getAppUserByInternalUser_Login(userDTO.getLogin())).isNotNull();
    }

    @Test
    @Transactional
    void registerUser_removeNonActivatedUserByLogin() {
        // Given
        Authority authorityUser = new Authority();
        authorityUser.setName(AuthoritiesConstants.USER);
        underTest.registerUser(userDTO, user.getPassword());

        // When
        User result = underTest.registerUser(userDTO, user.getPassword());

        // Then
        assertThat(result.getLogin()).isEqualTo(userDTO.getLogin());
        assertThat(result.isActivated()).isEqualTo(false);
        assertThat(result.getActivationKey()).isNotNull().isNotEmpty();
        assertThat(result.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(result.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(result.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(result.getImageUrl()).isEqualTo(userDTO.getImageUrl());
        assertThat(result.getLangKey()).isEqualTo(userDTO.getLangKey());
        assertThat(result.getAuthorities()).containsExactly(authorityUser);
        assertThat(appUserRepository.getAppUserByInternalUser_Login(userDTO.getLogin())).isNotNull();
    }

    @Test
    @Transactional
    void registerUser_removeNonActivatedUserByEmail() {
        // Given
        Authority authorityUser = new Authority();
        authorityUser.setName(AuthoritiesConstants.USER);
        underTest.registerUser(userDTO, user.getPassword());
        userDTO.setLogin("doejohn");

        // When
        User result = underTest.registerUser(userDTO, user.getPassword());

        // Then
        assertThat(result.getLogin()).isEqualTo(userDTO.getLogin());
        assertThat(result.isActivated()).isEqualTo(false);
        assertThat(result.getActivationKey()).isNotNull().isNotEmpty();
        assertThat(result.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(result.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(result.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(result.getImageUrl()).isEqualTo(userDTO.getImageUrl());
        assertThat(result.getLangKey()).isEqualTo(userDTO.getLangKey());
        assertThat(result.getAuthorities()).containsExactly(authorityUser);
        assertThat(appUserRepository.getAppUserByInternalUser_Login(userDTO.getLogin())).isNotNull();
    }

    @Test
    @Transactional
    void createUser_returnUser() {
        // Given
        Authority authorityUser = new Authority();
        authorityUser.setName(AuthoritiesConstants.USER);
        userDTO.getAuthorities().add(AuthoritiesConstants.USER);

        // When
        User result = underTest.createUser(userDTO);

        // Then
        assertThat(result.getLogin()).isEqualTo(userDTO.getLogin());
        assertThat(result.isActivated()).isEqualTo(true);
        assertThat(result.getActivationKey()).isNull();
        assertThat(result.getResetKey()).isNotNull().isNotEmpty();
        assertThat(result.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(result.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(result.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(result.getImageUrl()).isEqualTo(userDTO.getImageUrl());
        assertThat(result.getLangKey()).isEqualTo(userDTO.getLangKey());
        assertThat(result.getAuthorities()).containsExactly(authorityUser);
        assertThat(appUserRepository.getAppUserByInternalUser_Login(userDTO.getLogin())).isNotNull();
    }

    @Test
    @Transactional
    void deleteUser() {
        // Given
        User userToDelete = underTest.createUser(userDTO);
        assertThat(userRepository.findOneByLogin(userToDelete.getLogin())).isNotEmpty();

        // When
        underTest.deleteUser(userToDelete.getLogin());

        // Then
        assertThat(userRepository.findOneByLogin(userToDelete.getLogin())).isEmpty();
    }

    @Test
    @Transactional
    void assertThatNotActivatedUsersWithNotNullActivationKeyCreatedBefore3DaysAreDeleted() {
        // Given
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        user.setActivated(false);
        user.setActivationKey(RandomStringUtils.random(20));
        User dbUser = userRepository.saveAndFlush(user);
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.saveAndFlush(user);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<User> users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isNotEmpty();

        // When
        underTest.removeNotActivatedUsers();

        // Then
        users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isEmpty();
    }

    @Test
    @Transactional
    void assertThatNotActivatedUsersWithNullActivationKeyCreatedBefore3DaysAreNotDeleted() {
        // Given
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        user.setActivated(false);
        User dbUser = userRepository.saveAndFlush(user);
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.saveAndFlush(user);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<User> users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isEmpty();

        // When
        underTest.removeNotActivatedUsers();

        // Then
        Optional<User> maybeDbUser = userRepository.findById(dbUser.getId());
        assertThat(maybeDbUser).contains(dbUser);
    }
}
