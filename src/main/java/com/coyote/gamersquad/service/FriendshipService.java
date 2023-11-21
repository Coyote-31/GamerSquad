package com.coyote.gamersquad.service;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.Friendship;
import com.coyote.gamersquad.repository.FriendshipRepository;
import com.coyote.gamersquad.service.dto.FriendshipDTO;
import com.coyote.gamersquad.service.mapper.FriendshipMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Friendship}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class FriendshipService {

    private final Logger log = LoggerFactory.getLogger(FriendshipService.class);

    private final FriendshipRepository friendshipRepository;

    private final FriendshipMapper friendshipMapper;

    public FriendshipService(FriendshipRepository friendshipRepository, FriendshipMapper friendshipMapper) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipMapper = friendshipMapper;
    }

    /**
     * Save a friendship.
     *
     * @param friendshipDTO the entity to save.
     * @return the persisted entity.
     */
    public FriendshipDTO save(FriendshipDTO friendshipDTO) {
        log.debug("Request to save Friendship : {}", friendshipDTO);
        Friendship friendship = friendshipMapper.toEntity(friendshipDTO);
        friendship = friendshipRepository.save(friendship);
        return friendshipMapper.toDto(friendship);
    }

    /**
     * Update a friendship.
     *
     * @param friendshipDTO the entity to save.
     * @return the persisted entity.
     */
    public FriendshipDTO update(FriendshipDTO friendshipDTO) {
        log.debug("Request to update Friendship : {}", friendshipDTO);
        Friendship friendship = friendshipMapper.toEntity(friendshipDTO);
        friendship = friendshipRepository.save(friendship);
        return friendshipMapper.toDto(friendship);
    }

    /**
     * Partially update a friendship.
     *
     * @param friendshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FriendshipDTO> partialUpdate(FriendshipDTO friendshipDTO) {
        log.debug("Request to partially update Friendship : {}", friendshipDTO);

        return friendshipRepository
            .findById(friendshipDTO.getId())
            .map(existingFriendship -> {
                friendshipMapper.partialUpdate(existingFriendship, friendshipDTO);

                return existingFriendship;
            })
            .map(friendshipRepository::save)
            .map(friendshipMapper::toDto);
    }

    /**
     * Get all the friendships.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FriendshipDTO> findAll() {
        log.debug("Request to get all Friendships");
        return friendshipRepository.findAll().stream().map(friendshipMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one friendship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FriendshipDTO> findOne(Long id) {
        log.debug("Request to get Friendship : {}", id);
        return friendshipRepository.findById(id).map(friendshipMapper::toDto);
    }

    /**
     * Delete the friendship by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Friendship : {}", id);
        friendshipRepository.deleteById(id);
    }
}
