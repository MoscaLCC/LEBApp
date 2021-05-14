package com.leb.app.service;

import com.leb.app.service.dto.RidePathDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.RidePath}.
 */
public interface RidePathService {
    /**
     * Save a ridePath.
     *
     * @param ridePathDTO the entity to save.
     * @return the persisted entity.
     */
    RidePathDTO save(RidePathDTO ridePathDTO);

    /**
     * Partially updates a ridePath.
     *
     * @param ridePathDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RidePathDTO> partialUpdate(RidePathDTO ridePathDTO);

    /**
     * Get all the ridePaths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RidePathDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ridePath.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RidePathDTO> findOne(Long id);

    /**
     * Delete the "id" ridePath.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
