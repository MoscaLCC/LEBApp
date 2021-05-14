package com.leb.app.web.rest;

import com.leb.app.repository.ProducerRepository;
import com.leb.app.service.ProducerQueryService;
import com.leb.app.service.ProducerService;
import com.leb.app.service.criteria.ProducerCriteria;
import com.leb.app.service.dto.ProducerDTO;
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
 * REST controller for managing {@link com.leb.app.domain.Producer}.
 */
@RestController
@RequestMapping("/api")
public class ProducerResource {

    private final Logger log = LoggerFactory.getLogger(ProducerResource.class);

    private static final String ENTITY_NAME = "producer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProducerService producerService;

    private final ProducerRepository producerRepository;

    private final ProducerQueryService producerQueryService;

    public ProducerResource(
        ProducerService producerService,
        ProducerRepository producerRepository,
        ProducerQueryService producerQueryService
    ) {
        this.producerService = producerService;
        this.producerRepository = producerRepository;
        this.producerQueryService = producerQueryService;
    }

    /**
     * {@code POST  /producers} : Create a new producer.
     *
     * @param producerDTO the producerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new producerDTO, or with status {@code 400 (Bad Request)} if the producer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producers")
    public ResponseEntity<ProducerDTO> createProducer(@RequestBody ProducerDTO producerDTO) throws URISyntaxException {
        log.debug("REST request to save Producer : {}", producerDTO);
        if (producerDTO.getId() != null) {
            throw new BadRequestAlertException("A new producer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProducerDTO result = producerService.save(producerDTO);
        return ResponseEntity
            .created(new URI("/api/producers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producers/:id} : Updates an existing producer.
     *
     * @param id the id of the producerDTO to save.
     * @param producerDTO the producerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producerDTO,
     * or with status {@code 400 (Bad Request)} if the producerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the producerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producers/{id}")
    public ResponseEntity<ProducerDTO> updateProducer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProducerDTO producerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Producer : {}, {}", id, producerDTO);
        if (producerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, producerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!producerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProducerDTO result = producerService.save(producerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, producerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producers/:id} : Partial updates given fields of an existing producer, field will ignore if it is null
     *
     * @param id the id of the producerDTO to save.
     * @param producerDTO the producerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producerDTO,
     * or with status {@code 400 (Bad Request)} if the producerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the producerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the producerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProducerDTO> partialUpdateProducer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProducerDTO producerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Producer partially : {}, {}", id, producerDTO);
        if (producerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, producerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!producerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProducerDTO> result = producerService.partialUpdate(producerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, producerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /producers} : get all the producers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of producers in body.
     */
    @GetMapping("/producers")
    public ResponseEntity<List<ProducerDTO>> getAllProducers(ProducerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Producers by criteria: {}", criteria);
        Page<ProducerDTO> page = producerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producers/count} : count all the producers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/producers/count")
    public ResponseEntity<Long> countProducers(ProducerCriteria criteria) {
        log.debug("REST request to count Producers by criteria: {}", criteria);
        return ResponseEntity.ok().body(producerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /producers/:id} : get the "id" producer.
     *
     * @param id the id of the producerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the producerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producers/{id}")
    public ResponseEntity<ProducerDTO> getProducer(@PathVariable Long id) {
        log.debug("REST request to get Producer : {}", id);
        Optional<ProducerDTO> producerDTO = producerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(producerDTO);
    }

    /**
     * {@code DELETE  /producers/:id} : delete the "id" producer.
     *
     * @param id the id of the producerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producers/{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        log.debug("REST request to delete Producer : {}", id);
        producerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
