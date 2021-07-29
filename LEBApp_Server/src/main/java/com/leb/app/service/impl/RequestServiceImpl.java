package com.leb.app.service.impl;

import com.leb.app.domain.Request;
import com.leb.app.repository.RequestRepository;
import com.leb.app.service.RequestService;
import com.leb.app.service.dto.RequestDTO;
import com.leb.app.service.mapper.RequestMapper;
import java.util.Optional;
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
@Transactional
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }

    @Override
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public Optional<RequestDTO> partialUpdate(RequestDTO requestDTO) {
        log.debug("Request to partially update Request : {}", requestDTO);

        return requestRepository
            .findById(requestDTO.getId())
            .map(
                existingRequest -> {
                    requestMapper.partialUpdate(existingRequest, requestDTO);
                    return existingRequest;
                }
            )
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
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }

    @Override
    public void update(RequestDTO requestDTO, Long id) {
        Request request = requestRepository.findTopByIdEquals(id);

        request.setProductValue(requestDTO.getProductValue());
        request.setProductName(requestDTO.getProductName()); 
        request.setSource(requestDTO.getSource());
        request.setDestination(requestDTO.getDestination());
        request.setDestinationContact(requestDTO.getDestinationContact());
        request.setInitDate(requestDTO.getInitDate());
        request.setExpirationDate(requestDTO.getExpirationDate());
        request.setDescription(requestDTO.getDescription());
        request.setSpecialCharacteristics(requestDTO.getSpecialCharacteristics());

        requestRepository.saveAndFlush(request);
    }

}
