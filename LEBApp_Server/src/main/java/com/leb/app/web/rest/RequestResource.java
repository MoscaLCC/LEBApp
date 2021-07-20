package com.leb.app.web.rest;

import com.leb.app.domain.Producer;
import com.leb.app.domain.enumeration.Status;
import com.leb.app.repository.ProducerRepository;
import com.leb.app.repository.RequestRepository;
import com.leb.app.service.DimensionsService;
import com.leb.app.service.RequestQueryService;
import com.leb.app.service.RequestService;
import com.leb.app.service.RidePathService;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.dto.DimensionsDTO;
import com.leb.app.service.dto.RequestDTO;
import com.leb.app.service.dto.RidePathDTO;
import com.leb.app.service.mapper.ProducerMapper;
import com.leb.app.service.mapper.RequestMapper;
import com.leb.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jboss.jandex.VoidType;
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
 * REST controller for managing {@link com.leb.app.domain.Request}.
 */
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

    private final ProducerRepository producerRepository;

    private final ProducerMapper producerMapper;

    private final DimensionsService dimensionsService;

    private final RidePathService ridePathService;

    private final RequestMapper requestMapper;

    public RequestResource(RequestService requestService, RequestRepository requestRepository, RequestQueryService requestQueryService, ProducerRepository producerRepository, ProducerMapper producerMapper, DimensionsService dimensionsService, RidePathService ridePathService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
        this.requestQueryService = requestQueryService;
        this.producerRepository = producerRepository;
        this.producerMapper = producerMapper;
        this.dimensionsService = dimensionsService;
        this.ridePathService = ridePathService;
        this.requestMapper = requestMapper;
    }

    //METODO PARA CRIAR UM PEDIDO PARA PRODUTOR
    @PostMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> createRequest(
        @NotNull
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Request : {}, {}", id, requestDTO);


        Producer producer = producerRepository.findByUserInfoIdEquals(id);
        DimensionsDTO dimensions = dimensionsService.save(requestDTO.getDimensions());
        RidePathDTO ridePath = ridePathService.save(requestDTO.getRidePath());

        requestDTO.setProducer(producerMapper.toDto(producer));
        requestDTO.setDimensions(dimensions);
        requestDTO.setRidePath(ridePath);

        RequestDTO result = requestService.save(requestDTO);

        return ResponseEntity
            .created(new URI("/api/requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /*
    @PutMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> updateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
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
    }*/

    /*
    @PatchMapping(value = "/requests/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RequestDTO> partialUpdateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
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
    }*/

    //METODO PARA OBTER TODOS OS PEDIDOS
    /*@GetMapping("/requests")
    public ResponseEntity<List<RequestDTO>> getAllRequests(RequestCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Requests by criteria: {}", criteria);
        Page<RequestDTO> page = requestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/


    @GetMapping("/requests/{userId}/{profile}")
    public ResponseEntity<List<RequestDTO>> getAllByIdAndProfile(
        @PathVariable(value = "userId", required = false) final Long id,
        @PathVariable(value = "profile", required = false) final String profile
    ) {
        log.debug("{} Requeste with profile {}", id, profile);

        List<RequestDTO> requests;

        switch (profile) {
            case "Producer" :
                requests = requestMapper.toDto(requestRepository.findByProducerIdEquals(id));
                break;
            case "DeliveryMan" :
                requests = requestMapper.toDto(requestRepository.findByCollectorIdEquals(id));
                requests.addAll(requestMapper.toDto(requestRepository.findByDestributorIdEquals(id))); 
                break;
            case "Transporter" :
                requests = requestMapper.toDto(requestRepository.findByTransporterIdEquals(id));
                break;
            case "Point" :
                requests = requestMapper.toDto(requestRepository.findByOriginalPointIdEquals(id));
                requests.addAll(requestMapper.toDto(requestRepository.findByDestinationPointIdEquals(id)));
                break;
            default:
                requests = new ArrayList<RequestDTO>();
                break;
        }

        return ResponseEntity.ok().body(requests);
    }

    //METODO PARA RETORNAR O NUMERO DE PEDIDOS NO SISTEMA
    /*@GetMapping("/requests/count")
    public ResponseEntity<Long> countRequests(RequestCriteria criteria) {
        log.debug("REST request to count Requests by criteria: {}", criteria);
        return ResponseEntity.ok().body(requestQueryService.countByCriteria(criteria));
    }*/

    //METODO PARA OBTER A INFO DE UM PEDIDO
    /*@GetMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);
        Optional<RequestDTO> requestDTO = requestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestDTO);
    }*/

    //METODO PARA OBTER A INFO DE UM PEDIDO CASO SE SEJA O PRODUTOR
    @GetMapping("/requests/interface/{id}/{userId}")
    public ResponseEntity<RequestDTO> getRequestProducer(@PathVariable Long id, Long userId) {
        log.debug("REST request to get Request : {} from {}", id, userId);

        Optional<RequestDTO> requestDTO = requestService.findOne(id);
        RequestDTO request = new RequestDTO();

        if(requestDTO.isPresent()) request = requestDTO.get();

        if(request.getProducer().getId().equals(userId)) return ResponseUtil.wrapOrNotFound(requestDTO);
        else throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }


    //METODO PARA APAGAR UM PEDIDO
    /*@DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Transporter : {}", id);
        requestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }*/

    //METODO PARA APAGAR UM PEDIDO CASO SEJA O PRODUTOR
    @DeleteMapping("/requests/{id}/{userId}")
    public ResponseEntity<HttpStatus> deleteRequestProducer(@PathVariable Long id, Long userId) {
        log.debug("REST request to delete Request : {} from {}", id, userId);

        
        Optional<RequestDTO> opRequest = requestService.findOne(id);
        RequestDTO request = new RequestDTO();

        if(opRequest.isPresent()){
            request = opRequest.get();
        }
        
        if(request.getStatus().equals(Status.WAITING_COLLECTION) && request.getProducer().getId().equals(userId)){
            requestService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
