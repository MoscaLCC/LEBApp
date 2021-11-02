package com.leb.app.service;

import com.leb.app.domain.*; // for static metamodels
import com.leb.app.domain.UserInfo;
import com.leb.app.repository.UserInfoRepository;
import com.leb.app.service.criteria.UserInfoCriteria;
import com.leb.app.service.dto.UserInfoDTO;
import com.leb.app.service.mapper.UserInfoMapper;
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
 * Service for executing complex queries for {@link UserInfo} entities in the database.
 * The main input is a {@link UserInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserInfoDTO} or a {@link Page} of {@link UserInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserInfoQueryService extends QueryService<UserInfo> {

    private final Logger log = LoggerFactory.getLogger(UserInfoQueryService.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    public UserInfoQueryService(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * Return a {@link List} of {@link UserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfoDTO> findByCriteria(UserInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoMapper.toDto(userInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findByCriteria(UserInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.findAll(specification, page).map(userInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link UserInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserInfo> createSpecification(UserInfoCriteria criteria) {
        Specification<UserInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserInfo_.id));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), UserInfo_.phoneNumber));
            }
            if (criteria.getNib() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNib(), UserInfo_.nib));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNif(), UserInfo_.nif));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), UserInfo_.address));
            }
            if (criteria.getLinkSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkSocial(), UserInfo_.linkSocial));
            }
            if (criteria.getNumberRequests() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberRequests(), UserInfo_.numberRequests));
            }
            if (criteria.getPayedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayedValue(), UserInfo_.payedValue));
            }
            if (criteria.getValueToPay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueToPay(), UserInfo_.valueToPay));
            }
            if (criteria.getRanking() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRanking(), UserInfo_.ranking));
            }
            if (criteria.getNumberOfDeliveries() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfDeliveries(), UserInfo_.numberOfDeliveries));
            }
            if (criteria.getNumberOfKm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfKm(), UserInfo_.numberOfKm));
            }
            if (criteria.getRequestsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRequestsId(), root -> root.join(UserInfo_.requests, JoinType.LEFT).get(Request_.id))
                    );
            }
            if (criteria.getTransportationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransportationsId(),
                            root -> root.join(UserInfo_.transportations, JoinType.LEFT).get(Request_.id)
                        )
                    );
            }
            if (criteria.getPointId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPointId(), root -> root.join(UserInfo_.points, JoinType.LEFT).get(Point_.id))
                    );
            }
        }
        return specification;
    }
}
