package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.Transporter;
import com.leb.app.repository.TransporterRepository;
import com.leb.app.service.criteria.TransporterCriteria;
import com.leb.app.service.dto.TransporterDTO;
import com.leb.app.service.mapper.TransporterMapper;
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
 * Service for executing complex queries for {@link Transporter} entities in the database.
 * The main input is a {@link TransporterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransporterDTO} or a {@link Page} of {@link TransporterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransporterQueryService extends QueryService<Transporter> {

    private final Logger log = LoggerFactory.getLogger(TransporterQueryService.class);

    private final TransporterRepository transporterRepository;

    private final TransporterMapper transporterMapper;

    public TransporterQueryService(TransporterRepository transporterRepository, TransporterMapper transporterMapper) {
        this.transporterRepository = transporterRepository;
        this.transporterMapper = transporterMapper;
    }

    /**
     * Return a {@link List} of {@link TransporterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransporterDTO> findByCriteria(TransporterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transporter> specification = createSpecification(criteria);
        return transporterMapper.toDto(transporterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransporterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransporterDTO> findByCriteria(TransporterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transporter> specification = createSpecification(criteria);
        return transporterRepository.findAll(specification, page).map(transporterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransporterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transporter> specification = createSpecification(criteria);
        return transporterRepository.count(specification);
    }

    /**
     * Function to convert {@link TransporterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transporter> createSpecification(TransporterCriteria criteria) {
        Specification<Transporter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transporter_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Transporter_.name));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Transporter_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Transporter_.phoneNumber));
            }
            if (criteria.getNib() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNib(), Transporter_.nib));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNif(), Transporter_.nif));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), Transporter_.birthday));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Transporter_.address));
            }
            if (criteria.getPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoto(), Transporter_.photo));
            }
            if (criteria.getFavouriteTransport() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFavouriteTransport(), Transporter_.favouriteTransport));
            }
            if (criteria.getNumberOfDeliveries() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumberOfDeliveries(), Transporter_.numberOfDeliveries));
            }
            if (criteria.getNumberOfKm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfKm(), Transporter_.numberOfKm));
            }
            if (criteria.getReceivedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedValue(), Transporter_.receivedValue));
            }
            if (criteria.getValueToReceive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueToReceive(), Transporter_.valueToReceive));
            }
            if (criteria.getRanking() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRanking(), Transporter_.ranking));
            }
            if (criteria.getRidePathId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRidePathId(),
                            root -> root.join(Transporter_.ridePaths, JoinType.LEFT).get(RidePath_.id)
                        )
                    );
            }
            if (criteria.getZonesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getZonesId(), root -> root.join(Transporter_.zones, JoinType.LEFT).get(Zone_.id))
                    );
            }
        }
        return specification;
    }
}
