package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.Request;
import com.leb.app.repository.RequestRepository;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.dto.RequestDTO;
import com.leb.app.service.mapper.RequestMapper;
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
 * Service for executing complex queries for {@link Request} entities in the database.
 * The main input is a {@link RequestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequestDTO} or a {@link Page} of {@link RequestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequestQueryService extends QueryService<Request> {

    private final Logger log = LoggerFactory.getLogger(RequestQueryService.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    public RequestQueryService(RequestRepository requestRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }

    /**
     * Return a {@link List} of {@link RequestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequestDTO> findByCriteria(RequestCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Request> specification = createSpecification(criteria);
        return requestMapper.toDto(requestRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RequestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestDTO> findByCriteria(RequestCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Request> specification = createSpecification(criteria);
        return requestRepository.findAll(specification, page).map(requestMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequestCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Request> specification = createSpecification(criteria);
        return requestRepository.count(specification);
    }

    /**
     * Function to convert {@link RequestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Request> createSpecification(RequestCriteria criteria) {
        Specification<Request> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Request_.id));
            }
            if (criteria.getProductValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductValue(), Request_.productValue));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), Request_.productName));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), Request_.source));
            }
            if (criteria.getDestination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestination(), Request_.destination));
            }
            if (criteria.getDestinationContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinationContact(), Request_.destinationContact));
            }
            if (criteria.getInitDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInitDate(), Request_.initDate));
            }
            if (criteria.getExpirationDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpirationDate(), Request_.expirationDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Request_.description));
            }
            if (criteria.getSpecialCharacteristics() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSpecialCharacteristics(), Request_.specialCharacteristics));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Request_.weight));
            }
            if (criteria.getHight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHight(), Request_.hight));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Request_.width));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Request_.status));
            }
            if (criteria.getEstimatedDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstimatedDate(), Request_.estimatedDate));
            }
            if (criteria.getDeliveryTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryTime(), Request_.deliveryTime));
            }
            if (criteria.getShippingCosts() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShippingCosts(), Request_.shippingCosts));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRating(), Request_.rating));
            }
            if (criteria.getOwnerRequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOwnerRequestId(),
                            root -> root.join(Request_.ownerRequest, JoinType.LEFT).get(UserInfo_.id)
                        )
                    );
            }
            if (criteria.getTranporterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTranporterId(),
                            root -> root.join(Request_.tranporter, JoinType.LEFT).get(UserInfo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
