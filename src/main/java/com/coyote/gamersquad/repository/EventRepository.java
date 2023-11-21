package com.coyote.gamersquad.repository;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Event entity.
 */
@Repository
@GeneratedByJHipster
public interface EventRepository extends JpaRepository<Event, Long> {
    default Optional<Event> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Event> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Event> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct event from Event event left join fetch event.game",
        countQuery = "select count(distinct event) from Event event"
    )
    Page<Event> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct event from Event event left join fetch event.game")
    List<Event> findAllWithToOneRelationships();

    @Query("select event from Event event left join fetch event.game where event.id =:id")
    Optional<Event> findOneWithToOneRelationships(@Param("id") Long id);
}
