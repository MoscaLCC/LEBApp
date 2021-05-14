package com.leb.app.repository;

import com.leb.app.domain.Transporter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Transporter entity.
 */
@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long>, JpaSpecificationExecutor<Transporter> {
    @Query(
        value = "select distinct transporter from Transporter transporter left join fetch transporter.routes",
        countQuery = "select count(distinct transporter) from Transporter transporter"
    )
    Page<Transporter> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct transporter from Transporter transporter left join fetch transporter.routes")
    List<Transporter> findAllWithEagerRelationships();

    @Query("select transporter from Transporter transporter left join fetch transporter.routes where transporter.id =:id")
    Optional<Transporter> findOneWithEagerRelationships(@Param("id") Long id);
}
