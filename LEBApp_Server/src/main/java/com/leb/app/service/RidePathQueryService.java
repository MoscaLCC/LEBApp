package com.leb.app.service;

import java.util.List;

// for static metamodels
import com.leb.app.domain.RidePath;
import com.leb.app.domain.RidePath_;
import com.leb.app.repository.RidePathRepository;
import com.leb.app.service.criteria.RidePathCriteria;
import com.leb.app.service.dto.RidePathDTO;
import com.leb.app.service.mapper.RidePathMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link RidePath} entities in the database.
 * The main input is a {@link RidePathCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RidePathDTO} or a {@link Page} of {@link RidePathDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RidePathQueryService extends QueryService<RidePath> {

    private final Logger log = LoggerFactory.getLogger(RidePathQueryService.class);

    private final RidePathRepository ridePathRepository;

    private final RidePathMapper ridePathMapper;

    public RidePathQueryService(RidePathRepository ridePathRepository, RidePathMapper ridePathMapper) {
        this.ridePathRepository = ridePathRepository;
        this.ridePathMapper = ridePathMapper;
    }

    /**
     * Return a {@link List} of {@link RidePathDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RidePathDTO> findByCriteria(RidePathCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RidePath> specification = createSpecification(criteria);
        return ridePathMapper.toDto(ridePathRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RidePathDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RidePathDTO> findByCriteria(RidePathCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RidePath> specification = createSpecification(criteria);
        return ridePathRepository.findAll(specification, page).map(ridePathMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RidePathCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RidePath> specification = createSpecification(criteria);
        return ridePathRepository.count(specification);
    }

    /**
     * Function to convert {@link RidePathCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RidePath> createSpecification(RidePathCriteria criteria) {
        Specification<RidePath> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RidePath_.id));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), RidePath_.source));
            }
            if (criteria.getDestination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestination(), RidePath_.destination));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistance(), RidePath_.distance));
            }
            if (criteria.getEstimatedTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstimatedTime(), RidePath_.estimatedTime));
            }
            if (criteria.getRadius() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRadius(), RidePath_.radius));
            }
        }
        return specification;
    }
}
