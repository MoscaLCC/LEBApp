package com.leb.app.repository;

import com.leb.app.domain.RidePath;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RidePath entity.
 */
@Repository
public interface RidePathRepository extends JpaRepository<RidePath, Long>, JpaSpecificationExecutor<RidePath> {}
