package com.leb.app.repository;

import com.leb.app.domain.Request;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Request entity.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {

    Request findByIdEquals(Long id);

}
