package com.leb.app.repository;

import com.leb.app.domain.Dimensions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dimensions entity.
 */
@Repository
public interface DimensionsRepository extends JpaRepository<Dimensions, Long>, JpaSpecificationExecutor<Dimensions> {}
