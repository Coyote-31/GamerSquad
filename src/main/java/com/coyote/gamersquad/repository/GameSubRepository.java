package com.coyote.gamersquad.repository;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.domain.GameSub;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameSub entity.
 */
@Repository
@GeneratedByJHipster
public interface GameSubRepository extends JpaRepository<GameSub, Long> {
    default Optional<GameSub> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GameSub> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GameSub> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct gameSub from GameSub gameSub left join fetch gameSub.game",
        countQuery = "select count(distinct gameSub) from GameSub gameSub"
    )
    Page<GameSub> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct gameSub from GameSub gameSub left join fetch gameSub.game")
    List<GameSub> findAllWithToOneRelationships();

    @Query("select gameSub from GameSub gameSub left join fetch gameSub.game where gameSub.id =:id")
    Optional<GameSub> findOneWithToOneRelationships(@Param("id") Long id);
}
