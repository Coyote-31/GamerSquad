package com.coyote.gamersquad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.IntegrationTest;
import com.coyote.gamersquad.domain.AppUser;
import com.coyote.gamersquad.domain.Game;
import com.coyote.gamersquad.domain.GameSub;
import com.coyote.gamersquad.repository.GameSubRepository;
import com.coyote.gamersquad.service.GameSubService;
import com.coyote.gamersquad.service.dto.GameSubDTO;
import com.coyote.gamersquad.service.mapper.GameSubMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GameSubResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class GameSubResourceIT {

    private static final String ENTITY_API_URL = "/api/game-subs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameSubRepository gameSubRepository;

    @Mock
    private GameSubRepository gameSubRepositoryMock;

    @Autowired
    private GameSubMapper gameSubMapper;

    @Mock
    private GameSubService gameSubServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameSubMockMvc;

    private GameSub gameSub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameSub createEntity(EntityManager em) {
        GameSub gameSub = new GameSub();
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createEntity(em);
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        gameSub.setAppUser(appUser);
        // Add required entity
        Game game;
        if (TestUtil.findAll(em, Game.class).isEmpty()) {
            game = GameResourceIT.createEntity(em);
            em.persist(game);
            em.flush();
        } else {
            game = TestUtil.findAll(em, Game.class).get(0);
        }
        gameSub.setGame(game);
        return gameSub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameSub createUpdatedEntity(EntityManager em) {
        GameSub gameSub = new GameSub();
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createUpdatedEntity(em);
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        gameSub.setAppUser(appUser);
        // Add required entity
        Game game;
        if (TestUtil.findAll(em, Game.class).isEmpty()) {
            game = GameResourceIT.createUpdatedEntity(em);
            em.persist(game);
            em.flush();
        } else {
            game = TestUtil.findAll(em, Game.class).get(0);
        }
        gameSub.setGame(game);
        return gameSub;
    }

    @BeforeEach
    public void initTest() {
        gameSub = createEntity(em);
    }

    @Test
    @Transactional
    void createGameSub() throws Exception {
        int databaseSizeBeforeCreate = gameSubRepository.findAll().size();
        // Create the GameSub
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);
        restGameSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameSubDTO)))
            .andExpect(status().isCreated());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeCreate + 1);
        GameSub testGameSub = gameSubList.get(gameSubList.size() - 1);
    }

    @Test
    @Transactional
    void createGameSubWithExistingId() throws Exception {
        // Create the GameSub with an existing ID
        gameSub.setId(1L);
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);

        int databaseSizeBeforeCreate = gameSubRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameSubDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGameSubs() throws Exception {
        // Initialize the database
        gameSubRepository.saveAndFlush(gameSub);

        // Get all the gameSubList
        restGameSubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameSub.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGameSubsWithEagerRelationshipsIsEnabled() throws Exception {
        when(gameSubServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGameSubMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(gameSubServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGameSubsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(gameSubServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGameSubMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(gameSubRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGameSub() throws Exception {
        // Initialize the database
        gameSubRepository.saveAndFlush(gameSub);

        // Get the gameSub
        restGameSubMockMvc
            .perform(get(ENTITY_API_URL_ID, gameSub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameSub.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGameSub() throws Exception {
        // Get the gameSub
        restGameSubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGameSub() throws Exception {
        // Initialize the database
        gameSubRepository.saveAndFlush(gameSub);

        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();

        // Update the gameSub
        GameSub updatedGameSub = gameSubRepository.findById(gameSub.getId()).get();
        // Disconnect from session so that the updates on updatedGameSub are not directly saved in db
        em.detach(updatedGameSub);
        GameSubDTO gameSubDTO = gameSubMapper.toDto(updatedGameSub);

        restGameSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameSubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameSubDTO))
            )
            .andExpect(status().isOk());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
        GameSub testGameSub = gameSubList.get(gameSubList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingGameSub() throws Exception {
        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();
        gameSub.setId(count.incrementAndGet());

        // Create the GameSub
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameSubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGameSub() throws Exception {
        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();
        gameSub.setId(count.incrementAndGet());

        // Create the GameSub
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGameSub() throws Exception {
        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();
        gameSub.setId(count.incrementAndGet());

        // Create the GameSub
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameSubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameSubDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameSubWithPatch() throws Exception {
        // Initialize the database
        gameSubRepository.saveAndFlush(gameSub);

        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();

        // Update the gameSub using partial update
        GameSub partialUpdatedGameSub = new GameSub();
        partialUpdatedGameSub.setId(gameSub.getId());

        restGameSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameSub))
            )
            .andExpect(status().isOk());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
        GameSub testGameSub = gameSubList.get(gameSubList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateGameSubWithPatch() throws Exception {
        // Initialize the database
        gameSubRepository.saveAndFlush(gameSub);

        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();

        // Update the gameSub using partial update
        GameSub partialUpdatedGameSub = new GameSub();
        partialUpdatedGameSub.setId(gameSub.getId());

        restGameSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameSub))
            )
            .andExpect(status().isOk());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
        GameSub testGameSub = gameSubList.get(gameSubList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingGameSub() throws Exception {
        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();
        gameSub.setId(count.incrementAndGet());

        // Create the GameSub
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameSubDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGameSub() throws Exception {
        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();
        gameSub.setId(count.incrementAndGet());

        // Create the GameSub
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameSubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGameSub() throws Exception {
        int databaseSizeBeforeUpdate = gameSubRepository.findAll().size();
        gameSub.setId(count.incrementAndGet());

        // Create the GameSub
        GameSubDTO gameSubDTO = gameSubMapper.toDto(gameSub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameSubMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gameSubDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameSub in the database
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGameSub() throws Exception {
        // Initialize the database
        gameSubRepository.saveAndFlush(gameSub);

        int databaseSizeBeforeDelete = gameSubRepository.findAll().size();

        // Delete the gameSub
        restGameSubMockMvc
            .perform(delete(ENTITY_API_URL_ID, gameSub.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameSub> gameSubList = gameSubRepository.findAll();
        assertThat(gameSubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
