package com.leb.app.web.rest;

import com.leb.app.repository.RouteRepository;
import com.leb.app.service.RouteQueryService;
import com.leb.app.service.RouteService;
import com.leb.app.service.criteria.RouteCriteria;
import com.leb.app.service.dto.RouteDTO;
import com.leb.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.leb.app.domain.Route}.
 */
@RestController
@RequestMapping("/api")
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(RouteResource.class);

    private static final String ENTITY_NAME = "route";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RouteService routeService;

    private final RouteRepository routeRepository;

    private final RouteQueryService routeQueryService;

    public RouteResource(RouteService routeService, RouteRepository routeRepository, RouteQueryService routeQueryService) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.routeQueryService = routeQueryService;
    }

    /**
     * {@code POST  /routes} : Create a new route.
     *
     * @param routeDTO the routeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new routeDTO, or with status {@code 400 (Bad Request)} if the route has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/routes")
    public ResponseEntity<RouteDTO> createRoute(@RequestBody RouteDTO routeDTO) throws URISyntaxException {
        log.debug("REST request to save Route : {}", routeDTO);
        if (routeDTO.getId() != null) {
            throw new BadRequestAlertException("A new route cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RouteDTO result = routeService.save(routeDTO);
        return ResponseEntity
            .created(new URI("/api/routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /routes/:id} : Updates an existing route.
     *
     * @param id the id of the routeDTO to save.
     * @param routeDTO the routeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated routeDTO,
     * or with status {@code 400 (Bad Request)} if the routeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the routeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/routes/{id}")
    public ResponseEntity<RouteDTO> updateRoute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RouteDTO routeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Route : {}, {}", id, routeDTO);
        if (routeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, routeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!routeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RouteDTO result = routeService.save(routeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, routeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /routes/:id} : Partial updates given fields of an existing route, field will ignore if it is null
     *
     * @param id the id of the routeDTO to save.
     * @param routeDTO the routeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated routeDTO,
     * or with status {@code 400 (Bad Request)} if the routeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the routeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the routeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/routes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RouteDTO> partialUpdateRoute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RouteDTO routeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Route partially : {}, {}", id, routeDTO);
        if (routeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, routeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!routeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RouteDTO> result = routeService.partialUpdate(routeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, routeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /routes} : get all the routes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of routes in body.
     */
    @GetMapping("/routes")
    public ResponseEntity<List<RouteDTO>> getAllRoutes(RouteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Routes by criteria: {}", criteria);
        Page<RouteDTO> page = routeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /routes/count} : count all the routes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/routes/count")
    public ResponseEntity<Long> countRoutes(RouteCriteria criteria) {
        log.debug("REST request to count Routes by criteria: {}", criteria);
        return ResponseEntity.ok().body(routeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /routes/:id} : get the "id" route.
     *
     * @param id the id of the routeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the routeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/routes/{id}")
    public ResponseEntity<RouteDTO> getRoute(@PathVariable Long id) {
        log.debug("REST request to get Route : {}", id);
        Optional<RouteDTO> routeDTO = routeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(routeDTO);
    }

    /**
     * {@code DELETE  /routes/:id} : delete the "id" route.
     *
     * @param id the id of the routeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        log.debug("REST request to delete Route : {}", id);
        routeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
