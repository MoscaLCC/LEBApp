package com.leb.app.repository;

import java.util.List;

import com.leb.app.domain.Request;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Request entity.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {

    List<Request> findByProducerIdEquals(Long id);

    List<Request> findByCollectorIdEquals(Long id);

    List<Request> findByDestributorIdEquals(Long id);

    List<Request> findByTransporterIdEquals(Long id);

    List<Request> findByOriginalPointIdEquals(Long id);

    List<Request> findByDestinationPointIdEquals(Long id);

    Request findTopByIdEquals(Long id);

}
