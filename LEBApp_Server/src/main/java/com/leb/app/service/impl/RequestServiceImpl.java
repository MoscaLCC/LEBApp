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

    @Override
    @Transactional
    public Boolean virtualDelete(Long id) {
        log.debug("Request to Virtual Delete Request : {}", id);
        Boolean flag = false;
        Optional<Request> optRequest = requestRepository.findById(id);
        
        if(optRequest.isPresent()){
            Request request = optRequest.get();
            request.setStatus(Status.DELETED);
            requestRepository.save(request);
        }
        flag = true;
        return flag;
    }

    @Override
    @Transactional
    public Boolean virtualDeleteRestricted(Long id) {
        log.debug("Request to Virtual Delete Request : {}", id);
        Boolean flag = false;
        Optional<Request> optRequest = requestRepository.findById(id);
        
        if(optRequest.isPresent()){
            Request request = optRequest.get();
            if(request.getStatus().equals(Status.WAITING_COLLECTION)){
                request.setStatus(Status.DELETED);
            }
            requestRepository.save(request);
        }
        flag = true;
        return flag;
    }

    public Boolean inTheCriteria(RequestDTO request, RequestCriteriaDTO criteria){
        if(criteria.getOwnerRequest() != null && !request.getOwnerRequest().equals(Long.valueOf(criteria.getOwnerRequest())))
            return false;
        else if(criteria.getTransporter() != null && request.getTransporter() == null)
            return false;
        else if(criteria.getTransporter() != null && !request.getTransporter().equals(Long.valueOf(criteria.getTransporter())))
            return false;
        else if(criteria.getStatus() != null){
            if (criteria.getStatus().equals("OPENED")){
                if (request.getStatus().toString().equals("CLOSED") || request.getStatus().toString().equals("DELETED")){
                    return false;
                } else {
                    return true;
                }
            }
            else if (criteria.getStatus().equals("CLOSED")){
                if (request.getStatus().toString().equals("CLOSED") || request.getStatus().toString().equals("DELETED")){
                    return true;
                } else {
                    return false;
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
    public Boolean assignToUser(Long requestId, Long userId){
        Boolean flag = false;
        Request request = requestRepository.findByIdEquals(requestId);
        log.info("RequestID: {}, UserID: {}", requestId, userId);
        request.setTransporter(userId);
        request.setStatus(Status.IN_COLLECTION);
        requestRepository.save(request);
        flag = true;
        return flag;
    }

    @Override
    @Transactional
    public Boolean inTransit(Long requestId, Long code){
        Request request = requestRepository.findByIdEquals(requestId);
        Boolean flag = false;
        log.info("RequestID: {}, Code: {}", requestId, code);
        if (request.getOwnerRequest().equals(code)){
            request.setStatus(Status.IN_TRANSIT);
            requestRepository.save(request);
            flag = true;
        }
        return flag;
    }

    @Override
    @Transactional
    public Boolean closeRequest(Long requestId, Long code){
        Request request = requestRepository.findByIdEquals(requestId);
        Boolean flag = false;
        log.info("RequestID: {}, Code: {}", requestId, code);
        if (request.getOwnerRequest().equals(code)){
            request.setStatus(Status.CLOSED);
            requestRepository.save(request);
            flag = true;
        }
        return flag;
    }

    @Override
    @Transactional
    public Boolean updateRating(Long requestId, Double value){
        Boolean flag = false; 
        Request request = requestRepository.findByIdEquals(requestId);
        log.info("RequestID: {}, UserID: {}", requestId, value);
        request.setRating(value);
        requestRepository.save(request);
        flag = true;
        return flag;
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public Double calculateCosts(RequestDTO request){

        Double dimentionFactor = ((request.getHight() + request.getWidth() + request.getWeight()) / 3) / 100;
        
        List<Double> listValues = new ArrayList<>();
        
        listValues.add(dimentionFactor);
        listValues.add(request.getProductValue());
        
        Double sum = getSum(listValues);
        Double size = Double.valueOf(listValues.size());

        return round((1 + Math.cbrt(sum/size)), 2);
    }

    @Override
    public RequestDTO prepareNewRequest(RequestDTO request, Long userId){

        request.setExpirationDate(Instant.parse(request.getInitDate()).plus(48, ChronoUnit.HOURS).toString());

        request.setShippingCosts(calculateCosts(request));

        request.setOwnerRequest(userId);

        request.setStatus(Status.WAITING_COLLECTION);

        request.setRating(1.5);

        return request;
    }


    @Override
    public RequestDTO objectToUpdate(RequestDTO request, Long requestId){

        Optional<Request> optDbRequest = requestRepository.findById(requestId);
        Request finalRequest = new Request();

        if(optDbRequest.isPresent()){
            Request dbRequest = optDbRequest.get();
            finalRequest.setId(dbRequest.getId());
            finalRequest.setProductValue(request.getProductValue());
            finalRequest.setProductName(request.getProductName());
            finalRequest.setSource(request.getSource());
            finalRequest.setDestination(request.getDestination());
            finalRequest.setDestinationContactEmail(request.getDestinationContactEmail());
            finalRequest.setDestinationContactMobile(request.getDestinationContactMobile());
            finalRequest.setInitDate(request.getInitDate());
            finalRequest.setExpirationDate(Instant.parse(request.getInitDate()).plus(48, ChronoUnit.HOURS).toString());
            finalRequest.setSpecialCharacteristics(request.getSpecialCharacteristics());
            finalRequest.setWeight(request.getWeight());
            finalRequest.setHight(request.getHight());
            finalRequest.setWidth(request.getWidth());
            finalRequest.setStatus(dbRequest.getStatus());
            finalRequest.setShippingCosts(calculateCosts(request));
            finalRequest.setRating(dbRequest.getRating());
            finalRequest.setOwnerRequest(dbRequest.getOwnerRequest());
            finalRequest.setTransporter(dbRequest.getTransporter());
        }

        return requestMapper.toDto(finalRequest);
    }
}
