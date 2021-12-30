package com.leb.app.service;

import com.leb.app.service.dto.RequestCriteriaDTO;
import com.leb.app.service.dto.RequestDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.Request}.
 */
public interface RequestService {

    RequestDTO save(RequestDTO requestDTO);

    Optional<RequestDTO> partialUpdate(RequestDTO requestDTO);

    Page<RequestDTO> findAll(Pageable pageable);

    Optional<RequestDTO> findOne(Long id);

    void delete(Long id);

    List<RequestDTO> findAllByCriteria(RequestCriteriaDTO criteria);

    void assignToUser(Long requestId, Long userId);

    RequestDTO prepareNewRequest(RequestDTO request, Long userId);

    void virtualDelete(Long id);

    void virtualDeleteRestricted(Long id);

    RequestDTO objectToUpdate(RequestDTO request, Long requestId);
}
