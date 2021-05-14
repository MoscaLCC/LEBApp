package com.leb.app.service;

import com.leb.app.service.dto.DimensionsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.Dimensions}.
 */
public interface DimensionsService {
    /**
     * Save a dimensions.
     *
     * @param dimensionsDTO the entity to save.
     * @return the persisted entity.
     */
    DimensionsDTO save(DimensionsDTO dimensionsDTO);

    /**
     * Partially updates a dimensions.
     *
     * @param dimensionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DimensionsDTO> partialUpdate(DimensionsDTO dimensionsDTO);

    /**
     * Get all the dimensions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DimensionsDTO> findAll(Pageable pageable);
    /**
     * Get all the DimensionsDTO where Request is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DimensionsDTO> findAllWhereRequestIsNull();

    /**
     * Get the "id" dimensions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DimensionsDTO> findOne(Long id);

    /**
     * Delete the "id" dimensions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
