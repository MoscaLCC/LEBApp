package com.leb.app.service;

import com.leb.app.service.dto.DeliveryManDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.DeliveryMan}.
 */
public interface DeliveryManService {
    /**
     * Save a deliveryMan.
     *
     * @param deliveryManDTO the entity to save.
     * @return the persisted entity.
     */
    DeliveryManDTO save(DeliveryManDTO deliveryManDTO);

    /**
     * Partially updates a deliveryMan.
     *
     * @param deliveryManDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeliveryManDTO> partialUpdate(DeliveryManDTO deliveryManDTO);

    /**
     * Get all the deliveryMen.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeliveryManDTO> findAll(Pageable pageable);

    /**
     * Get the "id" deliveryMan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeliveryManDTO> findOne(Long id);

    /**
     * Delete the "id" deliveryMan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Boolean isDeliveryMan(Long userId);
}
