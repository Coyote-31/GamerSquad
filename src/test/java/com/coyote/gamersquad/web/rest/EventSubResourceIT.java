package com.coyote.gamersquad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Event;
import com.coyote.gamersquad.domain.EventSub;
import com.coyote.gamersquad.repository.EventSubRepository;
import com.coyote.gamersquad.service.dto.EventSubDTO;
import com.coyote.gamersquad.service.mapper.EventSubMapper;
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
 * Integration tests for the {@link EventSubResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class EventSubResourceIT {

    private static final Boolean DEFAULT_IS_ACCEPTED = false;
    private static final Boolean UPDATED_IS_ACCEPTED = true;

    private static final String ENTITY_API_URL = "/api/event-subs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventSubRepository eventSubRepository;

    @Autowired
    private EventSubMapper eventSubMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventSubMockMvc;

    private EventSub eventSub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventSub createEntity(EntityManager em) {
        EventSub eventSub = new EventSub().isAccepted(DEFAULT_IS_ACCEPTED);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        eventSub.setEvent(event);
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createEntity(em);
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        eventSub.setAppUser(appUser);
        return eventSub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventSub createUpdatedEntity(EntityManager em) {
        EventSub eventSub = new EventSub().isAccepted(UPDATED_IS_ACCEPTED);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createUpdatedEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        eventSub.setEvent(event);
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createUpdatedEntity(em);
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        eventSub.setAppUser(appUser);
        return eventSub;
    }

    @BeforeEach
    public void initTest() {
        eventSub = createEntity(em);
    }

    @Test
    @Transactional
    void createEventSub() throws Exception {
        int databaseSizeBeforeCreate = eventSubRepository.findAll().size();
        // Create the EventSub
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);
        restEventSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventSubDTO)))
            .andExpect(status().isCreated());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeCreate + 1);
        EventSub testEventSub = eventSubList.get(eventSubList.size() - 1);
        assertThat(testEventSub.getIsAccepted()).isEqualTo(DEFAULT_IS_ACCEPTED);
    }

    @Test
    @Transactional
    void createEventSubWithExistingId() throws Exception {
        // Create the EventSub with an existing ID
        eventSub.setId(1L);
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        int databaseSizeBeforeCreate = eventSubRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventSubDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsAcceptedIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventSubRepository.findAll().size();
        // set the field null
        eventSub.setIsAccepted(null);

        // Create the EventSub, which fails.
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        restEventSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventSubDTO)))
            .andExpect(status().isBadRequest());

        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventSubs() throws Exception {
        // Initialize the database
        eventSubRepository.saveAndFlush(eventSub);

        // Get all the eventSubList
        restEventSubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventSub.getId().intValue())))
            .andExpect(jsonPath("$.[*].isAccepted").value(hasItem(DEFAULT_IS_ACCEPTED.booleanValue())));
    }

    @Test
    @Transactional
    void getEventSub() throws Exception {
        // Initialize the database
        eventSubRepository.saveAndFlush(eventSub);

        // Get the eventSub
        restEventSubMockMvc
            .perform(get(ENTITY_API_URL_ID, eventSub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventSub.getId().intValue()))
            .andExpect(jsonPath("$.isAccepted").value(DEFAULT_IS_ACCEPTED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventSub() throws Exception {
        // Get the eventSub
        restEventSubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventSub() throws Exception {
        // Initialize the database
        eventSubRepository.saveAndFlush(eventSub);

        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();

        // Update the eventSub
        EventSub updatedEventSub = eventSubRepository.findById(eventSub.getId()).get();
        // Disconnect from session so that the updates on updatedEventSub are not directly saved in db
        em.detach(updatedEventSub);
        updatedEventSub.isAccepted(UPDATED_IS_ACCEPTED);
        EventSubDTO eventSubDTO = eventSubMapper.toDto(updatedEventSub);

        restEventSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventSubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventSubDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
        EventSub testEventSub = eventSubList.get(eventSubList.size() - 1);
        assertThat(testEventSub.getIsAccepted()).isEqualTo(UPDATED_IS_ACCEPTED);
    }

    @Test
    @Transactional
    void putNonExistingEventSub() throws Exception {
        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();
        eventSub.setId(count.incrementAndGet());

        // Create the EventSub
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventSubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventSub() throws Exception {
        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();
        eventSub.setId(count.incrementAndGet());

        // Create the EventSub
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventSub() throws Exception {
        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();
        eventSub.setId(count.incrementAndGet());

        // Create the EventSub
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventSubDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventSubWithPatch() throws Exception {
        // Initialize the database
        eventSubRepository.saveAndFlush(eventSub);

        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();

        // Update the eventSub using partial update
        EventSub partialUpdatedEventSub = new EventSub();
        partialUpdatedEventSub.setId(eventSub.getId());

        partialUpdatedEventSub.isAccepted(UPDATED_IS_ACCEPTED);

        restEventSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventSub))
            )
            .andExpect(status().isOk());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
        EventSub testEventSub = eventSubList.get(eventSubList.size() - 1);
        assertThat(testEventSub.getIsAccepted()).isEqualTo(UPDATED_IS_ACCEPTED);
    }

    @Test
    @Transactional
    void fullUpdateEventSubWithPatch() throws Exception {
        // Initialize the database
        eventSubRepository.saveAndFlush(eventSub);

        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();

        // Update the eventSub using partial update
        EventSub partialUpdatedEventSub = new EventSub();
        partialUpdatedEventSub.setId(eventSub.getId());

        partialUpdatedEventSub.isAccepted(UPDATED_IS_ACCEPTED);

        restEventSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventSub))
            )
            .andExpect(status().isOk());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
        EventSub testEventSub = eventSubList.get(eventSubList.size() - 1);
        assertThat(testEventSub.getIsAccepted()).isEqualTo(UPDATED_IS_ACCEPTED);
    }

    @Test
    @Transactional
    void patchNonExistingEventSub() throws Exception {
        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();
        eventSub.setId(count.incrementAndGet());

        // Create the EventSub
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventSubDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventSub() throws Exception {
        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();
        eventSub.setId(count.incrementAndGet());

        // Create the EventSub
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventSub() throws Exception {
        int databaseSizeBeforeUpdate = eventSubRepository.findAll().size();
        eventSub.setId(count.incrementAndGet());

        // Create the EventSub
        EventSubDTO eventSubDTO = eventSubMapper.toDto(eventSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventSubDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventSub in the database
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventSub() throws Exception {
        // Initialize the database
        eventSubRepository.saveAndFlush(eventSub);

        int databaseSizeBeforeDelete = eventSubRepository.findAll().size();

        // Delete the eventSub
        restEventSubMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventSub.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventSub> eventSubList = eventSubRepository.findAll();
        assertThat(eventSubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
