package com.leb.app.service.impl;

import com.leb.app.domain.Dimensions;
import com.leb.app.repository.DimensionsRepository;
import com.leb.app.service.DimensionsService;
import com.leb.app.service.dto.DimensionsDTO;
import com.leb.app.service.mapper.DimensionsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dimensions}.
 */
@Service
@Transactional
public class DimensionsServiceImpl implements DimensionsService {

    private final Logger log = LoggerFactory.getLogger(DimensionsServiceImpl.class);

    private final DimensionsRepository dimensionsRepository;

    private final DimensionsMapper dimensionsMapper;

    public DimensionsServiceImpl(DimensionsRepository dimensionsRepository, DimensionsMapper dimensionsMapper) {
        this.dimensionsRepository = dimensionsRepository;
        this.dimensionsMapper = dimensionsMapper;
    }

    @Override
    public DimensionsDTO save(DimensionsDTO dimensionsDTO) {
        log.debug("Request to save Dimensions : {}", dimensionsDTO);
        Dimensions dimensions = dimensionsMapper.toEntity(dimensionsDTO);
        dimensions = dimensionsRepository.save(dimensions);
        return dimensionsMapper.toDto(dimensions);
    }

    @Override
    public Optional<DimensionsDTO> partialUpdate(DimensionsDTO dimensionsDTO) {
        log.debug("Request to partially update Dimensions : {}", dimensionsDTO);

        return dimensionsRepository
            .findById(dimensionsDTO.getId())
            .map(
                existingDimensions -> {
                    dimensionsMapper.partialUpdate(existingDimensions, dimensionsDTO);
                    return existingDimensions;
                }
            )
            .map(dimensionsRepository::save)
            .map(dimensionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DimensionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dimensions");
        return dimensionsRepository.findAll(pageable).map(dimensionsMapper::toDto);
    }

    /**
     *  Get all the dimensions where Request is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DimensionsDTO> findAllWhereRequestIsNull() {
        log.debug("Request to get all dimensions where Request is null");
        return StreamSupport
            .stream(dimensionsRepository.findAll().spliterator(), false)
            .filter(dimensions -> dimensions.getRequest() == null)
            .map(dimensionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DimensionsDTO> findOne(Long id) {
        log.debug("Request to get Dimensions : {}", id);
        return dimensionsRepository.findById(id).map(dimensionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dimensions : {}", id);
        dimensionsRepository.deleteById(id);
    }
}
