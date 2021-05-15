package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.DeliveryMan;
import com.leb.app.repository.DeliveryManRepository;
import com.leb.app.service.criteria.DeliveryManCriteria;
import com.leb.app.service.dto.DeliveryManDTO;
import com.leb.app.service.mapper.DeliveryManMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DeliveryMan} entities in the database.
 * The main input is a {@link DeliveryManCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeliveryManDTO} or a {@link Page} of {@link DeliveryManDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeliveryManQueryService extends QueryService<DeliveryMan> {

    private final Logger log = LoggerFactory.getLogger(DeliveryManQueryService.class);

    private final DeliveryManRepository deliveryManRepository;

    private final DeliveryManMapper deliveryManMapper;

    public DeliveryManQueryService(DeliveryManRepository deliveryManRepository, DeliveryManMapper deliveryManMapper) {
        this.deliveryManRepository = deliveryManRepository;
        this.deliveryManMapper = deliveryManMapper;
    }

    /**
     * Return a {@link List} of {@link DeliveryManDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryManDTO> findByCriteria(DeliveryManCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeliveryMan> specification = createSpecification(criteria);
        return deliveryManMapper.toDto(deliveryManRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeliveryManDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryManDTO> findByCriteria(DeliveryManCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeliveryMan> specification = createSpecification(criteria);
        return deliveryManRepository.findAll(specification, page).map(deliveryManMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeliveryManCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeliveryMan> specification = createSpecification(criteria);
        return deliveryManRepository.count(specification);
    }

    /**
     * Function to convert {@link DeliveryManCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeliveryMan> createSpecification(DeliveryManCriteria criteria) {
        Specification<DeliveryMan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeliveryMan_.id));
            }
            if (criteria.getOpeningTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpeningTime(), DeliveryMan_.openingTime));
            }
            if (criteria.getNumberOfDeliveries() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumberOfDeliveries(), DeliveryMan_.numberOfDeliveries));
            }
            if (criteria.getNumberOfKm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfKm(), DeliveryMan_.numberOfKm));
            }
            if (criteria.getReceivedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedValue(), DeliveryMan_.receivedValue));
            }
            if (criteria.getValueToReceive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueToReceive(), DeliveryMan_.valueToReceive));
            }
            if (criteria.getRanking() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRanking(), DeliveryMan_.ranking));
            }
            if (criteria.getUserInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserInfoId(),
                            root -> root.join(DeliveryMan_.userInfo, JoinType.LEFT).get(UserInfo_.id)
                        )
                    );
            }
            if (criteria.getPointId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPointId(), root -> root.join(DeliveryMan_.point, JoinType.LEFT).get(Point_.id))
                    );
            }
        }
        return specification;
    }
}
