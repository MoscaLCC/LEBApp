package com.leb.app.web.rest;

import com.leb.app.repository.RidePathRepository;
import com.leb.app.service.RidePathQueryService;
import com.leb.app.service.RidePathService;
import com.leb.app.service.criteria.RidePathCriteria;
import com.leb.app.service.dto.RidePathDTO;
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
 * REST controller for managing {@link com.leb.app.domain.RidePath}.
 */
@RestController
@RequestMapping("/api")
public class RidePathResource {

    private final Logger log = LoggerFactory.getLogger(RidePathResource.class);

    private static final String ENTITY_NAME = "ridePath";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RidePathService ridePathService;

    private final RidePathRepository ridePathRepository;

    private final RidePathQueryService ridePathQueryService;

    public RidePathResource(
        RidePathService ridePathService,
        RidePathRepository ridePathRepository,
        RidePathQueryService ridePathQueryService
    ) {
        this.ridePathService = ridePathService;
        this.ridePathRepository = ridePathRepository;
        this.ridePathQueryService = ridePathQueryService;
    }

    /**
     * {@code POST  /ride-paths} : Create a new ridePath.
     *
     * @param ridePathDTO the ridePathDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ridePathDTO, or with status {@code 400 (Bad Request)} if the ridePath has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ride-paths")
    public ResponseEntity<RidePathDTO> createRidePath(@RequestBody RidePathDTO ridePathDTO) throws URISyntaxException {
        log.debug("REST request to save RidePath : {}", ridePathDTO);
        if (ridePathDTO.getId() != null) {
            throw new BadRequestAlertException("A new ridePath cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RidePathDTO result = ridePathService.save(ridePathDTO);
        return ResponseEntity
            .created(new URI("/api/ride-paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ride-paths/:id} : Updates an existing ridePath.
     *
     * @param id the id of the ridePathDTO to save.
     * @param ridePathDTO the ridePathDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ridePathDTO,
     * or with status {@code 400 (Bad Request)} if the ridePathDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ridePathDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ride-paths/{id}")
    public ResponseEntity<RidePathDTO> updateRidePath(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RidePathDTO ridePathDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RidePath : {}, {}", id, ridePathDTO);
        if (ridePathDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ridePathDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ridePathRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RidePathDTO result = ridePathService.save(ridePathDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ridePathDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ride-paths/:id} : Partial updates given fields of an existing ridePath, field will ignore if it is null
     *
     * @param id the id of the ridePathDTO to save.
     * @param ridePathDTO the ridePathDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ridePathDTO,
     * or with status {@code 400 (Bad Request)} if the ridePathDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ridePathDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ridePathDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ride-paths/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RidePathDTO> partialUpdateRidePath(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RidePathDTO ridePathDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RidePath partially : {}, {}", id, ridePathDTO);
        if (ridePathDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ridePathDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ridePathRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RidePathDTO> result = ridePathService.partialUpdate(ridePathDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ridePathDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ride-paths} : get all the ridePaths.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ridePaths in body.
     */
    @GetMapping("/ride-paths")
    public ResponseEntity<List<RidePathDTO>> getAllRidePaths(RidePathCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RidePaths by criteria: {}", criteria);
        Page<RidePathDTO> page = ridePathQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ride-paths/count} : count all the ridePaths.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ride-paths/count")
    public ResponseEntity<Long> countRidePaths(RidePathCriteria criteria) {
        log.debug("REST request to count RidePaths by criteria: {}", criteria);
        return ResponseEntity.ok().body(ridePathQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ride-paths/:id} : get the "id" ridePath.
     *
     * @param id the id of the ridePathDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ridePathDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ride-paths/{id}")
    public ResponseEntity<RidePathDTO> getRidePath(@PathVariable Long id) {
        log.debug("REST request to get RidePath : {}", id);
        Optional<RidePathDTO> ridePathDTO = ridePathService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ridePathDTO);
    }

    /**
     * {@code DELETE  /ride-paths/:id} : delete the "id" ridePath.
     *
     * @param id the id of the ridePathDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ride-paths/{id}")
    public ResponseEntity<Void> deleteRidePath(@PathVariable Long id) {
        log.debug("REST request to delete RidePath : {}", id);
        ridePathService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
