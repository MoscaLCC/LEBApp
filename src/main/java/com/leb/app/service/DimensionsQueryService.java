package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.Dimensions;
import com.leb.app.repository.DimensionsRepository;
import com.leb.app.service.criteria.DimensionsCriteria;
import com.leb.app.service.dto.DimensionsDTO;
import com.leb.app.service.mapper.DimensionsMapper;
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
 * Service for executing complex queries for {@link Dimensions} entities in the database.
 * The main input is a {@link DimensionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DimensionsDTO} or a {@link Page} of {@link DimensionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DimensionsQueryService extends QueryService<Dimensions> {

    private final Logger log = LoggerFactory.getLogger(DimensionsQueryService.class);

    private final DimensionsRepository dimensionsRepository;

    private final DimensionsMapper dimensionsMapper;

    public DimensionsQueryService(DimensionsRepository dimensionsRepository, DimensionsMapper dimensionsMapper) {
        this.dimensionsRepository = dimensionsRepository;
        this.dimensionsMapper = dimensionsMapper;
    }

    /**
     * Return a {@link List} of {@link DimensionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DimensionsDTO> findByCriteria(DimensionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dimensions> specification = createSpecification(criteria);
        return dimensionsMapper.toDto(dimensionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DimensionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DimensionsDTO> findByCriteria(DimensionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dimensions> specification = createSpecification(criteria);
        return dimensionsRepository.findAll(specification, page).map(dimensionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DimensionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dimensions> specification = createSpecification(criteria);
        return dimensionsRepository.count(specification);
    }

    /**
     * Function to convert {@link DimensionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Dimensions> createSpecification(DimensionsCriteria criteria) {
        Specification<Dimensions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Dimensions_.id));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Dimensions_.height));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Dimensions_.width));
            }
            if (criteria.getDepth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepth(), Dimensions_.depth));
            }
            if (criteria.getRequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRequestId(), root -> root.join(Dimensions_.request, JoinType.LEFT).get(Request_.id))
                    );
            }
        }
        return specification;
    }
}
