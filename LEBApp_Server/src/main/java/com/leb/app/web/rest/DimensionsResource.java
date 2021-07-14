package com.leb.app.web.rest;

import com.leb.app.domain.Dimensions;
import com.leb.app.repository.DimensionsRepository;
import com.leb.app.service.DimensionsQueryService;
import com.leb.app.service.DimensionsService;
import com.leb.app.service.criteria.DimensionsCriteria;
import com.leb.app.service.dto.DimensionsDTO;
import com.leb.app.service.mapper.DimensionsMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.leb.app.domain.Dimensions}.
 */
@RestController
@RequestMapping("/api")
public class DimensionsResource {

    private final Logger log = LoggerFactory.getLogger(DimensionsResource.class);

    private static final String ENTITY_NAME = "dimensions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DimensionsService dimensionsService;

    private final DimensionsRepository dimensionsRepository;

    private final DimensionsQueryService dimensionsQueryService;

    private final DimensionsMapper dimensionsMapper;

    public DimensionsResource(
        DimensionsService dimensionsService,
        DimensionsRepository dimensionsRepository,
        DimensionsQueryService dimensionsQueryService,
        DimensionsMapper dimensionsMapper
    ) {
        this.dimensionsService = dimensionsService;
        this.dimensionsRepository = dimensionsRepository;
        this.dimensionsQueryService = dimensionsQueryService;
        this.dimensionsMapper = dimensionsMapper;
    }

    /**
     * {@code POST  /dimensions} : Create a new dimensions.
     *
     * @param dimensionsDTO the dimensionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dimensionsDTO, or with status {@code 400 (Bad Request)} if the dimensions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dimensions")
    public ResponseEntity<Dimensions> createDimensions(@RequestBody DimensionsDTO dimensionsDTO) throws URISyntaxException {
        log.debug("REST request to save Dimensions : {}", dimensionsDTO);
        Dimensions result = dimensionsRepository.saveAndFlush(dimensionsMapper.toEntity(dimensionsDTO));
        return ResponseEntity
            .created(new URI("/api/dimensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dimensions/:id} : Updates an existing dimensions.
     *
     * @param id the id of the dimensionsDTO to save.
     * @param dimensionsDTO the dimensionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dimensionsDTO,
     * or with status {@code 400 (Bad Request)} if the dimensionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dimensionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dimensions/{id}")
    public ResponseEntity<DimensionsDTO> updateDimensions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DimensionsDTO dimensionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dimensions : {}, {}", id, dimensionsDTO);
        if (dimensionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dimensionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dimensionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DimensionsDTO result = dimensionsService.save(dimensionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dimensionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dimensions/:id} : Partial updates given fields of an existing dimensions, field will ignore if it is null
     *
     * @param id the id of the dimensionsDTO to save.
     * @param dimensionsDTO the dimensionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dimensionsDTO,
     * or with status {@code 400 (Bad Request)} if the dimensionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dimensionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dimensionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dimensions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DimensionsDTO> partialUpdateDimensions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DimensionsDTO dimensionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dimensions partially : {}, {}", id, dimensionsDTO);
        if (dimensionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dimensionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dimensionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DimensionsDTO> result = dimensionsService.partialUpdate(dimensionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dimensionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dimensions} : get all the dimensions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dimensions in body.
     */
    @GetMapping("/dimensions")
    public ResponseEntity<List<DimensionsDTO>> getAllDimensions(DimensionsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Dimensions by criteria: {}", criteria);
        Page<DimensionsDTO> page = dimensionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dimensions/count} : count all the dimensions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dimensions/count")
    public ResponseEntity<Long> countDimensions(DimensionsCriteria criteria) {
        log.debug("REST request to count Dimensions by criteria: {}", criteria);
        return ResponseEntity.ok().body(dimensionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dimensions/:id} : get the "id" dimensions.
     *
     * @param id the id of the dimensionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dimensionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dimensions/{id}")
    public ResponseEntity<DimensionsDTO> getDimensions(@PathVariable Long id) {
        log.debug("REST request to get Dimensions : {}", id);
        Optional<DimensionsDTO> dimensionsDTO = dimensionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dimensionsDTO);
    }

    /**
     * {@code DELETE  /dimensions/:id} : delete the "id" dimensions.
     *
     * @param id the id of the dimensionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dimensions/{id}")
    public ResponseEntity<Void> deleteDimensions(@PathVariable Long id) {
        log.debug("REST request to delete Dimensions : {}", id);
        dimensionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
