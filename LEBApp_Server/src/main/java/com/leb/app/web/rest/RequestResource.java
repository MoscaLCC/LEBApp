package com.leb.app.web.rest;

import com.leb.app.repository.RequestRepository;
import com.leb.app.service.RequestQueryService;
import com.leb.app.service.RequestService;
import com.leb.app.service.UserService;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.dto.AdminUserDTO;
import com.leb.app.service.dto.RequestCriteriaDTO;
import com.leb.app.service.dto.RequestDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestService requestService;

    private final RequestRepository requestRepository;

    private final RequestQueryService requestQueryService;

    private final UserService userService;

    public RequestResource(RequestService requestService, RequestRepository requestRepository, RequestQueryService requestQueryService, UserService userService) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
        this.requestQueryService = requestQueryService;
        this.userService = userService;
    }

    public Long getCurrentUser(){
        Optional<AdminUserDTO> userLogin = userService
        .getUserWithAuthorities()
        .map(AdminUserDTO::new);

        Long clientId = null;
        if (userLogin.isPresent()) {
            clientId = userLogin.get().getId();
        }
        return clientId;
    }

    @PostMapping("/requests/create")
    public ResponseEntity<RequestDTO> createRequest(@Valid @RequestBody RequestDTO requestDTO) throws URISyntaxException {
        log.debug("REST request to save Request : {}", requestDTO);

        Long clientId = this.getCurrentUser();

        if(clientId != null){
            requestDTO = requestService.prepareNewRequest(requestDTO, clientId);
            RequestDTO result = requestService.save(requestDTO);
            return ResponseEntity
                .created(new URI("/api/requests/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } else {
            log.debug("USER ID IS DIFERENTE THAT REQUEST ID");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> updateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestDTO requestDTO
    ){
        log.debug("REST request to update Request : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestDTO result = requestService.save(requestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestDTO> partialUpdateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestDTO requestDTO
    ){
        log.debug("REST request to partial update Request partially : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestDTO> result = requestService.partialUpdate(requestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString())
        );
    }

    @GetMapping("/requests")
    public ResponseEntity<List<RequestDTO>> getAllRequests(RequestCriteria criteria, Pageable pageable) {
        Page<RequestDTO> page = requestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/requests")
    public ResponseEntity<List<RequestDTO>> postAllRequests(@Valid @RequestBody RequestCriteriaDTO criteria) {
        log.debug("REST request to get Requests by criteria: {}", criteria);
        List<RequestDTO> list = requestService.findAllByCriteria(criteria);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/requests/count")
    public ResponseEntity<Long> countRequests(RequestCriteria criteria) {
        log.debug("REST request to count Requests by criteria: {}", criteria);
        return ResponseEntity.ok().body(requestQueryService.countByCriteria(criteria));
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);
        Optional<RequestDTO> requestDTO = requestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestDTO);
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        requestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/requests/{requestId}/{userId}/assign")
    public ResponseEntity<HttpStatus> assignToUser(
        @PathVariable(value = "requestId", required = true) final Long requestId,
        @PathVariable(value = "userId", required = true) final Long userId){
        requestService.assignToUser(requestId, userId);
        return ResponseEntity.ok().build();
    }
}
