package com.leb.app.service.impl;

import com.leb.app.domain.Point;
import com.leb.app.repository.PointRepository;
import com.leb.app.service.PointService;
import com.leb.app.service.dto.PointDTO;
import com.leb.app.service.mapper.PointMapper;

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
 * Service Implementation for managing {@link Point}.
 */
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final Logger log = LoggerFactory.getLogger(PointServiceImpl.class);

    private final PointRepository pointRepository;

    private final PointMapper pointMapper;

    public PointServiceImpl(PointRepository pointRepository, PointMapper pointMapper) {
        this.pointRepository = pointRepository;
        this.pointMapper = pointMapper;
    }

    @Override
    public PointDTO save(PointDTO pointDTO) {
        log.debug("Request to save Point : {}", pointDTO);
        Point point = pointMapper.toEntity(pointDTO);
        point = pointRepository.save(point);
        return pointMapper.toDto(point);
    }

    @Override
    public Optional<PointDTO> partialUpdate(PointDTO pointDTO) {
        log.debug("Request to partially update Point : {}", pointDTO);

        return pointRepository
            .findById(pointDTO.getId())
            .map(existingPoint -> {
                pointMapper.partialUpdate(existingPoint, pointDTO);

                return existingPoint;
            })
            .map(pointRepository::save)
            .map(pointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Points");
        return pointRepository.findAll(pageable).map(pointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointDTO> findAllList() {
        log.debug("Request to get all Points");
        return pointRepository.findAll().stream().map(pointMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PointDTO> findOne(Long id) {
        log.debug("Request to get Point : {}", id);
        return pointRepository.findById(id).map(pointMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Point : {}", id);
        pointRepository.deleteById(id);
    }

    @Override
    public PointDTO prepareNewPoint(PointDTO point, Long userId){

        point.setStatus(Long.valueOf(0));

        point.setNumberOfDeliveries(0);

        point.setOwnerPoint(userId);

        return point;
    }
}
