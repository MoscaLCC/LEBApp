package com.leb.app.web.rest;

import com.leb.app.repository.RequestRepository;
import com.leb.app.service.RequestQueryService;
import com.leb.app.service.RequestService;
import com.leb.app.service.UserService;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.dto.AdminUserDTO;
import com.leb.app.service.dto.RequestCriteriaDTO;
import com.leb.app.service.dto.RequestDTO;
import com.leb.app.service.mapper.RequestMapper;
import com.leb.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.leb.app.service.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final MailService mailService;

    private final RequestMapper requestMapper;

    public RequestResource(RequestService requestService, RequestRepository requestRepository, RequestQueryService requestQueryService, UserService userService, MailService mailService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
        this.requestQueryService = requestQueryService;
        this.userService = userService;
        this.mailService = mailService;
        this.requestMapper = requestMapper;
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

            requestService.initBalance(requestMapper.toEntity(result));
            
            String subject = "LEB New Request Nº" + result.getId();
            String content = "<p>Hello!!</p>" +
            "<p>Foi registado na nossa aplicação um pedido de entraga para ti!</p>" +
            "<p>Nº do pedido: " + result.getId() + "</p>" +
            "<p>Deve entregar este codigo ao transportador:</p>" +
            "<h2 style=\"text-align: center\">" + result.getOwnerRequest() + "</h2>" +
            "<a href=\"http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/request/view/" + result.getId() +"\">Siga a sua entrega aqui!</a>" +
            "<p>Obriado pela sua preferencia,</p>" +
            "<p>Equipa LEB.</p>";

            mailService.sendEmail(result.getDestinationContactEmail(), subject, content, false, true);
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

        RequestDTO result = requestService.save(requestService.objectToUpdate(requestDTO, id));
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


    @GetMapping("/requests/view/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);
        Optional<RequestDTO> requestDTO = requestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestDTO);
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        if(requestService.virtualDelete(id)){
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/requests/interface/{id}")
    public ResponseEntity<Void> deleteRequestInterface(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        if(requestService.virtualDeleteRestricted(id)){
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/requests/{requestId}/{userId}/assign")
    public ResponseEntity<HttpStatus> assignToUser(
        @PathVariable(value = "requestId", required = true) final Long requestId,
        @PathVariable(value = "userId", required = true) final Long userId){
        if(requestService.assignToUser(requestId, userId)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/requests/{requestId}/{code}/transit")
    public ResponseEntity<HttpStatus> inTransit(
        @PathVariable(value = "requestId", required = true) final Long requestId,
        @PathVariable(value = "code", required = true) final Long code){
        if(requestService.inTransit(requestId, code)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/requests/rating/{requestId}/{value}/")
    public ResponseEntity<HttpStatus> updateRating(
        @PathVariable(value = "requestId", required = true) final Long requestId,
        @PathVariable(value = "value", required = true) final Double value){
        if(requestService.updateRating(requestId, value)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/requests/close/{requestId}/{code}/")
    public ResponseEntity<HttpStatus> closeRequest(
        @PathVariable(value = "requestId", required = true) final Long requestId,
        @PathVariable(value = "code", required = true) final Long code){
        if(requestService.closeRequest(requestId, code)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
