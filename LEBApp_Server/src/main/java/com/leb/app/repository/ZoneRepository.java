package com.leb.app.repository;

import com.leb.app.domain.Zone;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Zone entity.
 */
@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long>, JpaSpecificationExecutor<Zone> {
    @Query(
        value = "select distinct zone from Zone zone left join fetch zone.transporters",
        countQuery = "select count(distinct zone) from Zone zone"
    )
    Page<Zone> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct zone from Zone zone left join fetch zone.transporters")
    List<Zone> findAllWithEagerRelationships();

    @Query("select zone from Zone zone left join fetch zone.transporters where zone.id =:id")
    Optional<Zone> findOneWithEagerRelationships(@Param("id") Long id);
}
