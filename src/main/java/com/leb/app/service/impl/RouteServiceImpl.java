package com.leb.app.service.impl;

import com.leb.app.domain.Route;
import com.leb.app.repository.RouteRepository;
import com.leb.app.service.RouteService;
import com.leb.app.service.dto.RouteDTO;
import com.leb.app.service.mapper.RouteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Route}.
 */
@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

    private final RouteRepository routeRepository;

    private final RouteMapper routeMapper;

    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    @Override
    public RouteDTO save(RouteDTO routeDTO) {
        log.debug("Request to save Route : {}", routeDTO);
        Route route = routeMapper.toEntity(routeDTO);
        route = routeRepository.save(route);
        return routeMapper.toDto(route);
    }

    @Override
    public Optional<RouteDTO> partialUpdate(RouteDTO routeDTO) {
        log.debug("Request to partially update Route : {}", routeDTO);

        return routeRepository
            .findById(routeDTO.getId())
            .map(
                existingRoute -> {
                    routeMapper.partialUpdate(existingRoute, routeDTO);
                    return existingRoute;
                }
            )
            .map(routeRepository::save)
            .map(routeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RouteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Routes");
        return routeRepository.findAll(pageable).map(routeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RouteDTO> findOne(Long id) {
        log.debug("Request to get Route : {}", id);
        return routeRepository.findById(id).map(routeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Route : {}", id);
        routeRepository.deleteById(id);
    }
}
