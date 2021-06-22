package com.leb.app.service;

import com.leb.app.service.dto.PointDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.Point}.
 */
public interface PointService {
    /**
     * Save a point.
     *
     * @param pointDTO the entity to save.
     * @return the persisted entity.
     */
    PointDTO save(PointDTO pointDTO);

    /**
     * Partially updates a point.
     *
     * @param pointDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PointDTO> partialUpdate(PointDTO pointDTO);

    /**
     * Get all the points.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PointDTO> findAll(Pageable pageable);

    /**
     * Get the "id" point.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PointDTO> findOne(Long id);

    /**
     * Delete the "id" point.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Boolean isPoint(Long userId);
}
