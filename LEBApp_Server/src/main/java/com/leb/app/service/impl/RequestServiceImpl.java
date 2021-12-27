package com.leb.app.service.impl;

import com.leb.app.domain.Request;
import com.leb.app.domain.enumeration.Status;
import com.leb.app.repository.RequestRepository;
import com.leb.app.service.RequestService;
import com.leb.app.service.dto.RequestCriteriaDTO;
import com.leb.app.service.dto.RequestDTO;
import com.leb.app.service.mapper.RequestMapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service

public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }

    @Override
    @Transactional
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    @Transactional
    public Optional<RequestDTO> partialUpdate(RequestDTO requestDTO) {
        log.debug("Request to partially update Request : {}", requestDTO);

        return requestRepository
            .findById(requestDTO.getId())
            .map(existingRequest -> {
                requestMapper.partialUpdate(existingRequest, requestDTO);

                return existingRequest;
            })
            .map(requestRepository::save)
            .map(requestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestRepository.findAll(pageable).map(requestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestDTO> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findById(id).map(requestMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }

    public Boolean inTheCriteria(RequestDTO request, RequestCriteriaDTO criteria){
        if(criteria.getOwnerRequest() != null && !request.getOwnerRequest().equals(Long.valueOf(criteria.getOwnerRequest())))
            return false;
        else if(criteria.getTransporter() != null && !request.getTransporter().equals(Long.valueOf(criteria.getTransporter())))
            return false;
        else if(criteria.getStatus() != null){
            if (criteria.getStatus().equals("OPENED")){
                if (request.getStatus().toString().equals("CLOSED")){
                    return false;
                } else {
                    return true;
                }
            }
            else if (!request.getStatus().toString().equals(criteria.getStatus()))
                return false;
            else
                return true;
        }
        else
            return true;
    }

    @Override
    @Transactional
    public List<RequestDTO> findAllByCriteria(RequestCriteriaDTO criteria){
        List<RequestDTO> total_list = requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toList());
        List<RequestDTO> filtered_list = new ArrayList<>();

        for (RequestDTO request : total_list){
            if(inTheCriteria(request, criteria)){
                filtered_list.add(request);
            }
        }
        return filtered_list;
    }

    @Override
    @Transactional
    public void assignToUser(Long requestId, Long userId){
        Request request = requestRepository.findByIdEquals(requestId);
        log.info("REQUEST: {}", request);
        log.info("RequestID: {}, UserID: {}", requestId, userId);
        request.setTransporter(userId);
        request.setStatus(Status.IN_COLLECTION);
        requestRepository.save(request);
    }

    public Double getMax(List<Double> values){
        Double max = 0.0;
        for(Double d : values){
            if(d > max){
                max = d;
            }
        }
        return max;
    }

    public Double getSum(List<Double> values){
        Double sum = 0.0;
        for(Double d : values){
            sum += d;
        }
        return sum;
    }

    @Override
    public RequestDTO prepareNewRequest(RequestDTO request, Long userId){

        Instant expirationsDate = Instant.parse(request.getInitDate()).plus(48, ChronoUnit.HOURS);
        request.setExpirationDate(expirationsDate.toString());

        List<Double> listValues = new ArrayList<>();
        listValues.add(10.0);
        listValues.add(request.getHight());
        listValues.add(request.getWidth());
        listValues.add(request.getWeight());
        listValues.add(request.getProductValue());

        Double max = getMax(listValues);
        Double sum = getSum(listValues);
        Double costs = sum / max;
        request.setShippingCosts(costs);

        request.setOwnerRequest(userId);

        request.setStatus(Status.WAITING_COLLECTION);

        request.setRating(1.5);

        return request;
    }
}
