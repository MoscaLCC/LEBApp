package com.leb.app.web.rest;

import com.leb.app.repository.PointRepository;
import com.leb.app.service.PointQueryService;
import com.leb.app.service.PointService;
import com.leb.app.service.criteria.PointCriteria;
import com.leb.app.service.dto.PointDTO;
import com.leb.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.leb.app.domain.Point}.
 */
@RestController
@RequestMapping("/api")
public class PointResource {

    private final Logger log = LoggerFactory.getLogger(PointResource.class);

    private static final String ENTITY_NAME = "point";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointService pointService;

    private final PointRepository pointRepository;

    private final PointQueryService pointQueryService;

    public PointResource(PointService pointService, PointRepository pointRepository, PointQueryService pointQueryService) {
        this.pointService = pointService;
        this.pointRepository = pointRepository;
        this.pointQueryService = pointQueryService;
    }

    /**
     * {@code POST  /points} : Create a new point.
     *
     * @param pointDTO the pointDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointDTO, or with status {@code 400 (Bad Request)} if the point has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/points")
    public ResponseEntity<PointDTO> createPoint(@Valid @RequestBody PointDTO pointDTO) throws URISyntaxException {
        log.debug("REST request to save Point : {}", pointDTO);
        if (pointDTO.getId() != null) {
            throw new BadRequestAlertException("A new point cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointDTO result = pointService.save(pointDTO);
        return ResponseEntity
            .created(new URI("/api/points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /points/:id} : Updates an existing point.
     *
     * @param id the id of the pointDTO to save.
     * @param pointDTO the pointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDTO,
     * or with status {@code 400 (Bad Request)} if the pointDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/points/{id}")
    public ResponseEntity<PointDTO> updatePoint(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PointDTO pointDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Point : {}, {}", id, pointDTO);
        if (pointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PointDTO result = pointService.save(pointDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pointDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /points/:id} : Partial updates given fields of an existing point, field will ignore if it is null
     *
     * @param id the id of the pointDTO to save.
     * @param pointDTO the pointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDTO,
     * or with status {@code 400 (Bad Request)} if the pointDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pointDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/points/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PointDTO> partialUpdatePoint(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PointDTO pointDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Point partially : {}, {}", id, pointDTO);
        if (pointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointDTO> result = pointService.partialUpdate(pointDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pointDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /points} : get all the points.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of points in body.
     */
    @GetMapping("/points")
    public ResponseEntity<List<PointDTO>> getAllPoints(PointCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Points by criteria: {}", criteria);
        Page<PointDTO> page = pointQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /points/count} : count all the points.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/points/count")
    public ResponseEntity<Long> countPoints(PointCriteria criteria) {
        log.debug("REST request to count Points by criteria: {}", criteria);
        return ResponseEntity.ok().body(pointQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /points/:id} : get the "id" point.
     *
     * @param id the id of the pointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/points/{id}")
    public ResponseEntity<PointDTO> getPoint(@PathVariable Long id) {
        log.debug("REST request to get Point : {}", id);
        Optional<PointDTO> pointDTO = pointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointDTO);
    }

    /**
     * {@code DELETE  /points/:id} : delete the "id" point.
     *
     * @param id the id of the pointDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/points/{id}")
    public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
        log.debug("REST request to delete Point : {}", id);
        pointService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
