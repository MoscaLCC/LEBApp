package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.Point;
import com.leb.app.repository.PointRepository;
import com.leb.app.service.criteria.PointCriteria;
import com.leb.app.service.dto.PointDTO;
import com.leb.app.service.mapper.PointMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Point} entities in the database.
 * The main input is a {@link PointCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PointDTO} or a {@link Page} of {@link PointDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PointQueryService extends QueryService<Point> {

    private final Logger log = LoggerFactory.getLogger(PointQueryService.class);

    private final PointRepository pointRepository;

    private final PointMapper pointMapper;

    public PointQueryService(PointRepository pointRepository, PointMapper pointMapper) {
        this.pointRepository = pointRepository;
        this.pointMapper = pointMapper;
    }

    /**
     * Return a {@link List} of {@link PointDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PointDTO> findByCriteria(PointCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Point> specification = createSpecification(criteria);
        return pointMapper.toDto(pointRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PointDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PointDTO> findByCriteria(PointCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Point> specification = createSpecification(criteria);
        return pointRepository.findAll(specification, page).map(pointMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PointCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Point> specification = createSpecification(criteria);
        return pointRepository.count(specification);
    }

    /**
     * Function to convert {@link PointCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Point> createSpecification(PointCriteria criteria) {
        Specification<Point> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Point_.id));
            }
            if (criteria.getOpeningTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpeningTime(), Point_.openingTime));
            }
            if (criteria.getClosingTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClosingTime(), Point_.closingTime));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Point_.address));
            }
            if (criteria.getNumberOfDeliveries() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfDeliveries(), Point_.numberOfDeliveries));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Point_.status));
            }
        }
        return specification;
    }
}
