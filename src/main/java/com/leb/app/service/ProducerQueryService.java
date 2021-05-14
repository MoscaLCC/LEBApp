package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.Producer;
import com.leb.app.repository.ProducerRepository;
import com.leb.app.service.criteria.ProducerCriteria;
import com.leb.app.service.dto.ProducerDTO;
import com.leb.app.service.mapper.ProducerMapper;
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
 * Service for executing complex queries for {@link Producer} entities in the database.
 * The main input is a {@link ProducerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProducerDTO} or a {@link Page} of {@link ProducerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProducerQueryService extends QueryService<Producer> {

    private final Logger log = LoggerFactory.getLogger(ProducerQueryService.class);

    private final ProducerRepository producerRepository;

    private final ProducerMapper producerMapper;

    public ProducerQueryService(ProducerRepository producerRepository, ProducerMapper producerMapper) {
        this.producerRepository = producerRepository;
        this.producerMapper = producerMapper;
    }

    /**
     * Return a {@link List} of {@link ProducerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProducerDTO> findByCriteria(ProducerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Producer> specification = createSpecification(criteria);
        return producerMapper.toDto(producerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProducerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProducerDTO> findByCriteria(ProducerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Producer> specification = createSpecification(criteria);
        return producerRepository.findAll(specification, page).map(producerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProducerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Producer> specification = createSpecification(criteria);
        return producerRepository.count(specification);
    }

    /**
     * Function to convert {@link ProducerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Producer> createSpecification(ProducerCriteria criteria) {
        Specification<Producer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Producer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Producer_.name));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), Producer_.mail));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Producer_.phoneNumber));
            }
            if (criteria.getNib() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNib(), Producer_.nib));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNif(), Producer_.nif));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), Producer_.birthday));
            }
            if (criteria.getAdress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdress(), Producer_.adress));
            }
            if (criteria.getPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoto(), Producer_.photo));
            }
            if (criteria.getLinkSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkSocial(), Producer_.linkSocial));
            }
            if (criteria.getNumberRequests() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberRequests(), Producer_.numberRequests));
            }
            if (criteria.getPayedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayedValue(), Producer_.payedValue));
            }
            if (criteria.getValueToPay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueToPay(), Producer_.valueToPay));
            }
            if (criteria.getRanking() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRanking(), Producer_.ranking));
            }
            if (criteria.getRequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRequestId(), root -> root.join(Producer_.requests, JoinType.LEFT).get(Request_.id))
                    );
            }
        }
        return specification;
    }
}
