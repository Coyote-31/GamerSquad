package com.coyote.gamersquad.service;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.FriendshipChat;
import com.coyote.gamersquad.repository.FriendshipChatRepository;
import com.coyote.gamersquad.service.dto.FriendshipChatDTO;
import com.coyote.gamersquad.service.mapper.FriendshipChatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FriendshipChat}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class FriendshipChatService {

    private final Logger log = LoggerFactory.getLogger(FriendshipChatService.class);

    private final FriendshipChatRepository friendshipChatRepository;

    private final FriendshipChatMapper friendshipChatMapper;

    public FriendshipChatService(FriendshipChatRepository friendshipChatRepository, FriendshipChatMapper friendshipChatMapper) {
        this.friendshipChatRepository = friendshipChatRepository;
        this.friendshipChatMapper = friendshipChatMapper;
    }

    /**
     * Save a friendshipChat.
     *
     * @param friendshipChatDTO the entity to save.
     * @return the persisted entity.
     */
    public FriendshipChatDTO save(FriendshipChatDTO friendshipChatDTO) {
        log.debug("Request to save FriendshipChat : {}", friendshipChatDTO);
        FriendshipChat friendshipChat = friendshipChatMapper.toEntity(friendshipChatDTO);
        friendshipChat = friendshipChatRepository.save(friendshipChat);
        return friendshipChatMapper.toDto(friendshipChat);
    }

    /**
     * Update a friendshipChat.
     *
     * @param friendshipChatDTO the entity to save.
     * @return the persisted entity.
     */
    public FriendshipChatDTO update(FriendshipChatDTO friendshipChatDTO) {
        log.debug("Request to update FriendshipChat : {}", friendshipChatDTO);
        FriendshipChat friendshipChat = friendshipChatMapper.toEntity(friendshipChatDTO);
        friendshipChat = friendshipChatRepository.save(friendshipChat);
        return friendshipChatMapper.toDto(friendshipChat);
    }

    /**
     * Partially update a friendshipChat.
     *
     * @param friendshipChatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FriendshipChatDTO> partialUpdate(FriendshipChatDTO friendshipChatDTO) {
        log.debug("Request to partially update FriendshipChat : {}", friendshipChatDTO);

        return friendshipChatRepository
            .findById(friendshipChatDTO.getId())
            .map(existingFriendshipChat -> {
                friendshipChatMapper.partialUpdate(existingFriendshipChat, friendshipChatDTO);

                return existingFriendshipChat;
            })
            .map(friendshipChatRepository::save)
            .map(friendshipChatMapper::toDto);
    }

    /**
     * Get all the friendshipChats.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FriendshipChatDTO> findAll() {
        log.debug("Request to get all FriendshipChats");
        return friendshipChatRepository
            .findAll()
            .stream()
            .map(friendshipChatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one friendshipChat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FriendshipChatDTO> findOne(Long id) {
        log.debug("Request to get FriendshipChat : {}", id);
        return friendshipChatRepository.findById(id).map(friendshipChatMapper::toDto);
    }

    /**
     * Delete the friendshipChat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FriendshipChat : {}", id);
        friendshipChatRepository.deleteById(id);
    }
}
