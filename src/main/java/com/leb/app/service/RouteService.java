package com.leb.app.service;

import com.leb.app.service.dto.RouteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.Route}.
 */
public interface RouteService {
    /**
     * Save a route.
     *
     * @param routeDTO the entity to save.
     * @return the persisted entity.
     */
    RouteDTO save(RouteDTO routeDTO);

    /**
     * Partially updates a route.
     *
     * @param routeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RouteDTO> partialUpdate(RouteDTO routeDTO);

    /**
     * Get all the routes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RouteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" route.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RouteDTO> findOne(Long id);

    /**
     * Delete the "id" route.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
