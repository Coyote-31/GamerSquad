package com.coyote.gamersquad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.domain.FriendshipChat;
import com.coyote.gamersquad.repository.FriendshipChatRepository;
import com.coyote.gamersquad.service.dto.FriendshipChatDTO;
import com.coyote.gamersquad.service.mapper.FriendshipChatMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FriendshipChatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class FriendshipChatResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_SEND_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEND_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/friendship-chats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FriendshipChatRepository friendshipChatRepository;

    @Autowired
    private FriendshipChatMapper friendshipChatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendshipChatMockMvc;

    private FriendshipChat friendshipChat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendshipChat createEntity(EntityManager em) {
        FriendshipChat friendshipChat = new FriendshipChat().message(DEFAULT_MESSAGE).sendAt(DEFAULT_SEND_AT);
        // Add required entity
        Friendship friendship;
        if (TestUtil.findAll(em, Friendship.class).isEmpty()) {
            friendship = FriendshipResourceIT.createEntity(em);
            em.persist(friendship);
            em.flush();
        } else {
            friendship = TestUtil.findAll(em, Friendship.class).get(0);
        }
        friendshipChat.setFriendship(friendship);
        return friendshipChat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendshipChat createUpdatedEntity(EntityManager em) {
        FriendshipChat friendshipChat = new FriendshipChat().message(UPDATED_MESSAGE).sendAt(UPDATED_SEND_AT);
        // Add required entity
        Friendship friendship;
        if (TestUtil.findAll(em, Friendship.class).isEmpty()) {
            friendship = FriendshipResourceIT.createUpdatedEntity(em);
            em.persist(friendship);
            em.flush();
        } else {
            friendship = TestUtil.findAll(em, Friendship.class).get(0);
        }
        friendshipChat.setFriendship(friendship);
        return friendshipChat;
    }

    @BeforeEach
    public void initTest() {
        friendshipChat = createEntity(em);
    }

    @Test
    @Transactional
    void createFriendshipChat() throws Exception {
        int databaseSizeBeforeCreate = friendshipChatRepository.findAll().size();
        // Create the FriendshipChat
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);
        restFriendshipChatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeCreate + 1);
        FriendshipChat testFriendshipChat = friendshipChatList.get(friendshipChatList.size() - 1);
        assertThat(testFriendshipChat.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testFriendshipChat.getSendAt()).isEqualTo(DEFAULT_SEND_AT);
    }

    @Test
    @Transactional
    void createFriendshipChatWithExistingId() throws Exception {
        // Create the FriendshipChat with an existing ID
        friendshipChat.setId(1L);
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        int databaseSizeBeforeCreate = friendshipChatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendshipChatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendshipChatRepository.findAll().size();
        // set the field null
        friendshipChat.setMessage(null);

        // Create the FriendshipChat, which fails.
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        restFriendshipChatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isBadRequest());

        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSendAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendshipChatRepository.findAll().size();
        // set the field null
        friendshipChat.setSendAt(null);

        // Create the FriendshipChat, which fails.
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        restFriendshipChatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isBadRequest());

        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFriendshipChats() throws Exception {
        // Initialize the database
        friendshipChatRepository.saveAndFlush(friendshipChat);

        // Get all the friendshipChatList
        restFriendshipChatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendshipChat.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].sendAt").value(hasItem(DEFAULT_SEND_AT.toString())));
    }

    @Test
    @Transactional
    void getFriendshipChat() throws Exception {
        // Initialize the database
        friendshipChatRepository.saveAndFlush(friendshipChat);

        // Get the friendshipChat
        restFriendshipChatMockMvc
            .perform(get(ENTITY_API_URL_ID, friendshipChat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friendshipChat.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.sendAt").value(DEFAULT_SEND_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFriendshipChat() throws Exception {
        // Get the friendshipChat
        restFriendshipChatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFriendshipChat() throws Exception {
        // Initialize the database
        friendshipChatRepository.saveAndFlush(friendshipChat);

        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();

        // Update the friendshipChat
        FriendshipChat updatedFriendshipChat = friendshipChatRepository.findById(friendshipChat.getId()).get();
        // Disconnect from session so that the updates on updatedFriendshipChat are not directly saved in db
        em.detach(updatedFriendshipChat);
        updatedFriendshipChat.message(UPDATED_MESSAGE).sendAt(UPDATED_SEND_AT);
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(updatedFriendshipChat);

        restFriendshipChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, friendshipChatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isOk());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
        FriendshipChat testFriendshipChat = friendshipChatList.get(friendshipChatList.size() - 1);
        assertThat(testFriendshipChat.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testFriendshipChat.getSendAt()).isEqualTo(UPDATED_SEND_AT);
    }

    @Test
    @Transactional
    void putNonExistingFriendshipChat() throws Exception {
        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();
        friendshipChat.setId(count.incrementAndGet());

        // Create the FriendshipChat
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendshipChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, friendshipChatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFriendshipChat() throws Exception {
        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();
        friendshipChat.setId(count.incrementAndGet());

        // Create the FriendshipChat
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendshipChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFriendshipChat() throws Exception {
        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();
        friendshipChat.setId(count.incrementAndGet());

        // Create the FriendshipChat
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendshipChatMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFriendshipChatWithPatch() throws Exception {
        // Initialize the database
        friendshipChatRepository.saveAndFlush(friendshipChat);

        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();

        // Update the friendshipChat using partial update
        FriendshipChat partialUpdatedFriendshipChat = new FriendshipChat();
        partialUpdatedFriendshipChat.setId(friendshipChat.getId());

        partialUpdatedFriendshipChat.message(UPDATED_MESSAGE);

        restFriendshipChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriendshipChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriendshipChat))
            )
            .andExpect(status().isOk());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
        FriendshipChat testFriendshipChat = friendshipChatList.get(friendshipChatList.size() - 1);
        assertThat(testFriendshipChat.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testFriendshipChat.getSendAt()).isEqualTo(DEFAULT_SEND_AT);
    }

    @Test
    @Transactional
    void fullUpdateFriendshipChatWithPatch() throws Exception {
        // Initialize the database
        friendshipChatRepository.saveAndFlush(friendshipChat);

        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();

        // Update the friendshipChat using partial update
        FriendshipChat partialUpdatedFriendshipChat = new FriendshipChat();
        partialUpdatedFriendshipChat.setId(friendshipChat.getId());

        partialUpdatedFriendshipChat.message(UPDATED_MESSAGE).sendAt(UPDATED_SEND_AT);

        restFriendshipChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriendshipChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriendshipChat))
            )
            .andExpect(status().isOk());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
        FriendshipChat testFriendshipChat = friendshipChatList.get(friendshipChatList.size() - 1);
        assertThat(testFriendshipChat.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testFriendshipChat.getSendAt()).isEqualTo(UPDATED_SEND_AT);
    }

    @Test
    @Transactional
    void patchNonExistingFriendshipChat() throws Exception {
        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();
        friendshipChat.setId(count.incrementAndGet());

        // Create the FriendshipChat
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendshipChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, friendshipChatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFriendshipChat() throws Exception {
        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();
        friendshipChat.setId(count.incrementAndGet());

        // Create the FriendshipChat
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendshipChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFriendshipChat() throws Exception {
        int databaseSizeBeforeUpdate = friendshipChatRepository.findAll().size();
        friendshipChat.setId(count.incrementAndGet());

        // Create the FriendshipChat
        FriendshipChatDTO friendshipChatDTO = friendshipChatMapper.toDto(friendshipChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendshipChatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendshipChatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FriendshipChat in the database
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFriendshipChat() throws Exception {
        // Initialize the database
        friendshipChatRepository.saveAndFlush(friendshipChat);

        int databaseSizeBeforeDelete = friendshipChatRepository.findAll().size();

        // Delete the friendshipChat
        restFriendshipChatMockMvc
            .perform(delete(ENTITY_API_URL_ID, friendshipChat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FriendshipChat> friendshipChatList = friendshipChatRepository.findAll();
        assertThat(friendshipChatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
