package com.leb.app.service.impl;

import com.leb.app.domain.DeliveryMan;
import com.leb.app.repository.DeliveryManRepository;
import com.leb.app.service.DeliveryManService;
import com.leb.app.service.dto.DeliveryManDTO;
import com.leb.app.service.mapper.DeliveryManMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeliveryMan}.
 */
@Service
@Transactional
public class DeliveryManServiceImpl implements DeliveryManService {

    private final Logger log = LoggerFactory.getLogger(DeliveryManServiceImpl.class);

    private final DeliveryManRepository deliveryManRepository;

    private final DeliveryManMapper deliveryManMapper;

    public DeliveryManServiceImpl(DeliveryManRepository deliveryManRepository, DeliveryManMapper deliveryManMapper) {
        this.deliveryManRepository = deliveryManRepository;
        this.deliveryManMapper = deliveryManMapper;
    }

    @Override
    public DeliveryManDTO save(DeliveryManDTO deliveryManDTO) {
        log.debug("Request to save DeliveryMan : {}", deliveryManDTO);
        DeliveryMan deliveryMan = deliveryManMapper.toEntity(deliveryManDTO);
        deliveryMan = deliveryManRepository.save(deliveryMan);
        return deliveryManMapper.toDto(deliveryMan);
    }

    @Override
    public Optional<DeliveryManDTO> partialUpdate(DeliveryManDTO deliveryManDTO) {
        log.debug("Request to partially update DeliveryMan : {}", deliveryManDTO);

        return deliveryManRepository
            .findById(deliveryManDTO.getId())
            .map(
                existingDeliveryMan -> {
                    deliveryManMapper.partialUpdate(existingDeliveryMan, deliveryManDTO);
                    return existingDeliveryMan;
                }
            )
            .map(deliveryManRepository::save)
            .map(deliveryManMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryManDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeliveryMen");
        return deliveryManRepository.findAll(pageable).map(deliveryManMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryManDTO> findOne(Long id) {
        log.debug("Request to get DeliveryMan : {}", id);
        return deliveryManRepository.findById(id).map(deliveryManMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryMan : {}", id);
        deliveryManRepository.deleteById(id);
    }
}
