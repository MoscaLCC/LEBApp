package com.leb.app.service.impl;

import com.leb.app.domain.RidePath;
import com.leb.app.repository.RidePathRepository;
import com.leb.app.service.RidePathService;
import com.leb.app.service.dto.RidePathDTO;
import com.leb.app.service.mapper.RidePathMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RidePath}.
 */
@Service
@Transactional
public class RidePathServiceImpl implements RidePathService {

    private final Logger log = LoggerFactory.getLogger(RidePathServiceImpl.class);

    private final RidePathRepository ridePathRepository;

    private final RidePathMapper ridePathMapper;

    public RidePathServiceImpl(RidePathRepository ridePathRepository, RidePathMapper ridePathMapper) {
        this.ridePathRepository = ridePathRepository;
        this.ridePathMapper = ridePathMapper;
    }

    @Override
    public RidePathDTO save(RidePathDTO ridePathDTO) {
        log.debug("Request to save RidePath : {}", ridePathDTO);
        RidePath ridePath = ridePathMapper.toEntity(ridePathDTO);
        ridePath = ridePathRepository.save(ridePath);
        return ridePathMapper.toDto(ridePath);
    }

    @Override
    public Optional<RidePathDTO> partialUpdate(RidePathDTO ridePathDTO) {
        log.debug("Request to partially update RidePath : {}", ridePathDTO);

        return ridePathRepository
            .findById(ridePathDTO.getId())
            .map(existingRidePath -> {
                ridePathMapper.partialUpdate(existingRidePath, ridePathDTO);

                return existingRidePath;
            })
            .map(ridePathRepository::save)
            .map(ridePathMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RidePathDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RidePaths");
        return ridePathRepository.findAll(pageable).map(ridePathMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RidePathDTO> findOne(Long id) {
        log.debug("Request to get RidePath : {}", id);
        return ridePathRepository.findById(id).map(ridePathMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RidePath : {}", id);
        ridePathRepository.deleteById(id);
    }
}
