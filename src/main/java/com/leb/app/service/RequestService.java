package com.leb.app.service;

import com.leb.app.service.dto.RequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.Request}.
 */
public interface RequestService {
    /**
     * Save a request.
     *
     * @param requestDTO the entity to save.
     * @return the persisted entity.
     */
    RequestDTO save(RequestDTO requestDTO);

    /**
     * Partially updates a request.
     *
     * @param requestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestDTO> partialUpdate(RequestDTO requestDTO);

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" request.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestDTO> findOne(Long id);

    /**
     * Delete the "id" request.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
