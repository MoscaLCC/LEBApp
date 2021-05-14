package com.leb.app.web.rest;

import com.leb.app.repository.TransporterRepository;
import com.leb.app.service.TransporterQueryService;
import com.leb.app.service.TransporterService;
import com.leb.app.service.criteria.TransporterCriteria;
import com.leb.app.service.dto.TransporterDTO;
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
 * REST controller for managing {@link com.leb.app.domain.Transporter}.
 */
@RestController
@RequestMapping("/api")
public class TransporterResource {

    private final Logger log = LoggerFactory.getLogger(TransporterResource.class);

    private static final String ENTITY_NAME = "transporter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransporterService transporterService;

    private final TransporterRepository transporterRepository;

    private final TransporterQueryService transporterQueryService;

    public TransporterResource(
        TransporterService transporterService,
        TransporterRepository transporterRepository,
        TransporterQueryService transporterQueryService
    ) {
        this.transporterService = transporterService;
        this.transporterRepository = transporterRepository;
        this.transporterQueryService = transporterQueryService;
    }

    /**
     * {@code POST  /transporters} : Create a new transporter.
     *
     * @param transporterDTO the transporterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transporterDTO, or with status {@code 400 (Bad Request)} if the transporter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transporters")
    public ResponseEntity<TransporterDTO> createTransporter(@RequestBody TransporterDTO transporterDTO) throws URISyntaxException {
        log.debug("REST request to save Transporter : {}", transporterDTO);
        if (transporterDTO.getId() != null) {
            throw new BadRequestAlertException("A new transporter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransporterDTO result = transporterService.save(transporterDTO);
        return ResponseEntity
            .created(new URI("/api/transporters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transporters/:id} : Updates an existing transporter.
     *
     * @param id the id of the transporterDTO to save.
     * @param transporterDTO the transporterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporterDTO,
     * or with status {@code 400 (Bad Request)} if the transporterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transporterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transporters/{id}")
    public ResponseEntity<TransporterDTO> updateTransporter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransporterDTO transporterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Transporter : {}, {}", id, transporterDTO);
        if (transporterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transporterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transporterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransporterDTO result = transporterService.save(transporterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transporterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transporters/:id} : Partial updates given fields of an existing transporter, field will ignore if it is null
     *
     * @param id the id of the transporterDTO to save.
     * @param transporterDTO the transporterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporterDTO,
     * or with status {@code 400 (Bad Request)} if the transporterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transporterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transporterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transporters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TransporterDTO> partialUpdateTransporter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransporterDTO transporterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transporter partially : {}, {}", id, transporterDTO);
        if (transporterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transporterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transporterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransporterDTO> result = transporterService.partialUpdate(transporterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transporterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transporters} : get all the transporters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transporters in body.
     */
    @GetMapping("/transporters")
    public ResponseEntity<List<TransporterDTO>> getAllTransporters(TransporterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Transporters by criteria: {}", criteria);
        Page<TransporterDTO> page = transporterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transporters/count} : count all the transporters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transporters/count")
    public ResponseEntity<Long> countTransporters(TransporterCriteria criteria) {
        log.debug("REST request to count Transporters by criteria: {}", criteria);
        return ResponseEntity.ok().body(transporterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transporters/:id} : get the "id" transporter.
     *
     * @param id the id of the transporterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transporterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transporters/{id}")
    public ResponseEntity<TransporterDTO> getTransporter(@PathVariable Long id) {
        log.debug("REST request to get Transporter : {}", id);
        Optional<TransporterDTO> transporterDTO = transporterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transporterDTO);
    }

    /**
     * {@code DELETE  /transporters/:id} : delete the "id" transporter.
     *
     * @param id the id of the transporterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transporters/{id}")
    public ResponseEntity<Void> deleteTransporter(@PathVariable Long id) {
        log.debug("REST request to delete Transporter : {}", id);
        transporterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
