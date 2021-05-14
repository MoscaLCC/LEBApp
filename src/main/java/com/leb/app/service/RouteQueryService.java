package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.Route;
import com.leb.app.repository.RouteRepository;
import com.leb.app.service.criteria.RouteCriteria;
import com.leb.app.service.dto.RouteDTO;
import com.leb.app.service.mapper.RouteMapper;
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
 * Service for executing complex queries for {@link Route} entities in the database.
 * The main input is a {@link RouteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RouteDTO} or a {@link Page} of {@link RouteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RouteQueryService extends QueryService<Route> {

    private final Logger log = LoggerFactory.getLogger(RouteQueryService.class);

    private final RouteRepository routeRepository;

    private final RouteMapper routeMapper;

    public RouteQueryService(RouteRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    /**
     * Return a {@link List} of {@link RouteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RouteDTO> findByCriteria(RouteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Route> specification = createSpecification(criteria);
        return routeMapper.toDto(routeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RouteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RouteDTO> findByCriteria(RouteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Route> specification = createSpecification(criteria);
        return routeRepository.findAll(specification, page).map(routeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RouteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Route> specification = createSpecification(criteria);
        return routeRepository.count(specification);
    }

    /**
     * Function to convert {@link RouteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Route> createSpecification(RouteCriteria criteria) {
        Specification<Route> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Route_.id));
            }
        }
        return specification;
    }
}
