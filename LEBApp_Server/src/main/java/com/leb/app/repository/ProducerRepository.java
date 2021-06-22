package com.leb.app.repository;

import com.leb.app.domain.Producer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Producer entity.
 */
@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long>, JpaSpecificationExecutor<Producer> {

    Boolean existsByUserInfoId(Long UserId);

}
