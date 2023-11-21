package com.coyote.gamersquad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.EventChat;
import com.coyote.gamersquad.repository.EventChatRepository;
import com.coyote.gamersquad.service.dto.EventChatDTO;
import com.coyote.gamersquad.service.mapper.EventChatMapper;
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
 * Integration tests for the {@link EventChatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class EventChatResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_SEND_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEND_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/event-chats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventChatRepository eventChatRepository;

    @Autowired
    private EventChatMapper eventChatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventChatMockMvc;

    private EventChat eventChat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventChat createEntity(EntityManager em) {
        EventChat eventChat = new EventChat().message(DEFAULT_MESSAGE).sendAt(DEFAULT_SEND_AT);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        eventChat.setEvent(event);
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createEntity(em);
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        eventChat.setAppUser(appUser);
        return eventChat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventChat createUpdatedEntity(EntityManager em) {
        EventChat eventChat = new EventChat().message(UPDATED_MESSAGE).sendAt(UPDATED_SEND_AT);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createUpdatedEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        eventChat.setEvent(event);
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createUpdatedEntity(em);
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        eventChat.setAppUser(appUser);
        return eventChat;
    }

    @BeforeEach
    public void initTest() {
        eventChat = createEntity(em);
    }

    @Test
    @Transactional
    void createEventChat() throws Exception {
        int databaseSizeBeforeCreate = eventChatRepository.findAll().size();
        // Create the EventChat
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);
        restEventChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventChatDTO)))
            .andExpect(status().isCreated());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeCreate + 1);
        EventChat testEventChat = eventChatList.get(eventChatList.size() - 1);
        assertThat(testEventChat.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEventChat.getSendAt()).isEqualTo(DEFAULT_SEND_AT);
    }

    @Test
    @Transactional
    void createEventChatWithExistingId() throws Exception {
        // Create the EventChat with an existing ID
        eventChat.setId(1L);
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        int databaseSizeBeforeCreate = eventChatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventChatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventChatRepository.findAll().size();
        // set the field null
        eventChat.setMessage(null);

        // Create the EventChat, which fails.
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        restEventChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventChatDTO)))
            .andExpect(status().isBadRequest());

        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSendAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventChatRepository.findAll().size();
        // set the field null
        eventChat.setSendAt(null);

        // Create the EventChat, which fails.
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        restEventChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventChatDTO)))
            .andExpect(status().isBadRequest());

        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventChats() throws Exception {
        // Initialize the database
        eventChatRepository.saveAndFlush(eventChat);

        // Get all the eventChatList
        restEventChatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventChat.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].sendAt").value(hasItem(DEFAULT_SEND_AT.toString())));
    }

    @Test
    @Transactional
    void getEventChat() throws Exception {
        // Initialize the database
        eventChatRepository.saveAndFlush(eventChat);

        // Get the eventChat
        restEventChatMockMvc
            .perform(get(ENTITY_API_URL_ID, eventChat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventChat.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.sendAt").value(DEFAULT_SEND_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEventChat() throws Exception {
        // Get the eventChat
        restEventChatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventChat() throws Exception {
        // Initialize the database
        eventChatRepository.saveAndFlush(eventChat);

        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();

        // Update the eventChat
        EventChat updatedEventChat = eventChatRepository.findById(eventChat.getId()).get();
        // Disconnect from session so that the updates on updatedEventChat are not directly saved in db
        em.detach(updatedEventChat);
        updatedEventChat.message(UPDATED_MESSAGE).sendAt(UPDATED_SEND_AT);
        EventChatDTO eventChatDTO = eventChatMapper.toDto(updatedEventChat);

        restEventChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventChatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventChatDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
        EventChat testEventChat = eventChatList.get(eventChatList.size() - 1);
        assertThat(testEventChat.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEventChat.getSendAt()).isEqualTo(UPDATED_SEND_AT);
    }

    @Test
    @Transactional
    void putNonExistingEventChat() throws Exception {
        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();
        eventChat.setId(count.incrementAndGet());

        // Create the EventChat
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventChatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventChat() throws Exception {
        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();
        eventChat.setId(count.incrementAndGet());

        // Create the EventChat
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventChat() throws Exception {
        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();
        eventChat.setId(count.incrementAndGet());

        // Create the EventChat
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventChatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventChatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventChatWithPatch() throws Exception {
        // Initialize the database
        eventChatRepository.saveAndFlush(eventChat);

        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();

        // Update the eventChat using partial update
        EventChat partialUpdatedEventChat = new EventChat();
        partialUpdatedEventChat.setId(eventChat.getId());

        restEventChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventChat))
            )
            .andExpect(status().isOk());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
        EventChat testEventChat = eventChatList.get(eventChatList.size() - 1);
        assertThat(testEventChat.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEventChat.getSendAt()).isEqualTo(DEFAULT_SEND_AT);
    }

    @Test
    @Transactional
    void fullUpdateEventChatWithPatch() throws Exception {
        // Initialize the database
        eventChatRepository.saveAndFlush(eventChat);

        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();

        // Update the eventChat using partial update
        EventChat partialUpdatedEventChat = new EventChat();
        partialUpdatedEventChat.setId(eventChat.getId());

        partialUpdatedEventChat.message(UPDATED_MESSAGE).sendAt(UPDATED_SEND_AT);

        restEventChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventChat))
            )
            .andExpect(status().isOk());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
        EventChat testEventChat = eventChatList.get(eventChatList.size() - 1);
        assertThat(testEventChat.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEventChat.getSendAt()).isEqualTo(UPDATED_SEND_AT);
    }

    @Test
    @Transactional
    void patchNonExistingEventChat() throws Exception {
        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();
        eventChat.setId(count.incrementAndGet());

        // Create the EventChat
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventChatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventChat() throws Exception {
        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();
        eventChat.setId(count.incrementAndGet());

        // Create the EventChat
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventChatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventChat() throws Exception {
        int databaseSizeBeforeUpdate = eventChatRepository.findAll().size();
        eventChat.setId(count.incrementAndGet());

        // Create the EventChat
        EventChatDTO eventChatDTO = eventChatMapper.toDto(eventChat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventChatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventChatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventChat in the database
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventChat() throws Exception {
        // Initialize the database
        eventChatRepository.saveAndFlush(eventChat);

        int databaseSizeBeforeDelete = eventChatRepository.findAll().size();

        // Delete the eventChat
        restEventChatMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventChat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventChat> eventChatList = eventChatRepository.findAll();
        assertThat(eventChatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
