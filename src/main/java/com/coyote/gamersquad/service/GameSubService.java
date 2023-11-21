package com.coyote.gamersquad.service;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.GameSub;
import com.coyote.gamersquad.repository.GameSubRepository;
import com.coyote.gamersquad.service.dto.GameSubDTO;
import com.coyote.gamersquad.service.mapper.GameSubMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GameSub}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class GameSubService {

    private final Logger log = LoggerFactory.getLogger(GameSubService.class);

    private final GameSubRepository gameSubRepository;

    private final GameSubMapper gameSubMapper;

    public GameSubService(GameSubRepository gameSubRepository, GameSubMapper gameSubMapper) {
        this.gameSubRepository = gameSubRepository;
        this.gameSubMapper = gameSubMapper;
    }

    /**
     * Save a gameSub.
     *
     * @param gameSubDTO the entity to save.
     * @return the persisted entity.
     */
    public GameSubDTO save(GameSubDTO gameSubDTO) {
        log.debug("Request to save GameSub : {}", gameSubDTO);
        GameSub gameSub = gameSubMapper.toEntity(gameSubDTO);
        gameSub = gameSubRepository.save(gameSub);
        return gameSubMapper.toDto(gameSub);
    }

    /**
     * Update a gameSub.
     *
     * @param gameSubDTO the entity to save.
     * @return the persisted entity.
     */
    public GameSubDTO update(GameSubDTO gameSubDTO) {
        log.debug("Request to update GameSub : {}", gameSubDTO);
        GameSub gameSub = gameSubMapper.toEntity(gameSubDTO);
        gameSub = gameSubRepository.save(gameSub);
        return gameSubMapper.toDto(gameSub);
    }

    /**
     * Partially update a gameSub.
     *
     * @param gameSubDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GameSubDTO> partialUpdate(GameSubDTO gameSubDTO) {
        log.debug("Request to partially update GameSub : {}", gameSubDTO);

        return gameSubRepository
            .findById(gameSubDTO.getId())
            .map(existingGameSub -> {
                gameSubMapper.partialUpdate(existingGameSub, gameSubDTO);

                return existingGameSub;
            })
            .map(gameSubRepository::save)
            .map(gameSubMapper::toDto);
    }

    /**
     * Get all the gameSubs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GameSubDTO> findAll() {
        log.debug("Request to get all GameSubs");
        return gameSubRepository.findAll().stream().map(gameSubMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the gameSubs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GameSubDTO> findAllWithEagerRelationships(Pageable pageable) {
        return gameSubRepository.findAllWithEagerRelationships(pageable).map(gameSubMapper::toDto);
    }

    /**
     * Get one gameSub by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GameSubDTO> findOne(Long id) {
        log.debug("Request to get GameSub : {}", id);
        return gameSubRepository.findOneWithEagerRelationships(id).map(gameSubMapper::toDto);
    }

    /**
     * Delete the gameSub by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GameSub : {}", id);
        gameSubRepository.deleteById(id);
    }
}
