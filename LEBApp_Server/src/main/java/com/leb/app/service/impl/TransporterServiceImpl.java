package com.leb.app.service.impl;

import com.leb.app.domain.Transporter;
import com.leb.app.repository.TransporterRepository;
import com.leb.app.service.TransporterService;
import com.leb.app.service.dto.TransporterDTO;
import com.leb.app.service.mapper.TransporterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transporter}.
 */
@Service
@Transactional
public class TransporterServiceImpl implements TransporterService {

    private final Logger log = LoggerFactory.getLogger(TransporterServiceImpl.class);

    private final TransporterRepository transporterRepository;

    private final TransporterMapper transporterMapper;

    public TransporterServiceImpl(TransporterRepository transporterRepository, TransporterMapper transporterMapper) {
        this.transporterRepository = transporterRepository;
        this.transporterMapper = transporterMapper;
    }

    @Override
    public TransporterDTO save(TransporterDTO transporterDTO) {
        log.debug("Request to save Transporter : {}", transporterDTO);
        Transporter transporter = transporterMapper.toEntity(transporterDTO);
        transporter = transporterRepository.save(transporter);
        return transporterMapper.toDto(transporter);
    }

    @Override
    public Optional<TransporterDTO> partialUpdate(TransporterDTO transporterDTO) {
        log.debug("Request to partially update Transporter : {}", transporterDTO);

        return transporterRepository
            .findById(transporterDTO.getId())
            .map(
                existingTransporter -> {
                    transporterMapper.partialUpdate(existingTransporter, transporterDTO);
                    return existingTransporter;
                }
            )
            .map(transporterRepository::save)
            .map(transporterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransporterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transporters");
        return transporterRepository.findAll(pageable).map(transporterMapper::toDto);
    }

    public Page<TransporterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transporterRepository.findAllWithEagerRelationships(pageable).map(transporterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransporterDTO> findOne(Long id) {
        log.debug("Request to get Transporter : {}", id);
        return transporterRepository.findOneWithEagerRelationships(id).map(transporterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transporter : {}", id);
        transporterRepository.deleteById(id);
    }
}
