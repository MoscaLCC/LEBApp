package com.leb.app.service;

import com.leb.app.service.dto.TransporterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.Transporter}.
 */
public interface TransporterService {
    /**
     * Save a transporter.
     *
     * @param transporterDTO the entity to save.
     * @return the persisted entity.
     */
    TransporterDTO save(TransporterDTO transporterDTO);

    /**
     * Partially updates a transporter.
     *
     * @param transporterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransporterDTO> partialUpdate(TransporterDTO transporterDTO);

    /**
     * Get all the transporters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransporterDTO> findAll(Pageable pageable);

    /**
     * Get all the transporters with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransporterDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" transporter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransporterDTO> findOne(Long id);

    /**
     * Delete the "id" transporter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
