package com.leb.app.repository;

import com.leb.app.domain.DeliveryMan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeliveryMan entity.
 */

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long>, JpaSpecificationExecutor<DeliveryMan> {

    Boolean existsByUserInfoId(Long UserId);

}
