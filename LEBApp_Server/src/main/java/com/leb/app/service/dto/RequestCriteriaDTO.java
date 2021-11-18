package com.leb.app.service.dto;

import com.leb.app.domain.enumeration.Status;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.criteria.StatusFilter;
import java.io.Serializable;
import tech.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link com.leb.app.domain.Request} entity. This class is used
 * in {@link com.leb.app.web.rest.RequestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link } class are used, we need to use
 * fix type specific filters.
 */
public class RequestCriteriaDTO implements Serializable{

    private Status status;

    private Long ownerRequestId;

    private Long tranporterId;


    public RequestCriteriaDTO() {
        super();
    }

    public RequestCriteriaDTO(Status status, Long ownerRequestId,
            Long tranporterId) {
                super();
        this.status = status;
        this.ownerRequestId = ownerRequestId;
        this.tranporterId = tranporterId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getOwnerRequestId() {
        return ownerRequestId;
    }

    public void setOwnerRequestId(Long ownerRequestId) {
        this.ownerRequestId = ownerRequestId;
    }

    public Long getTranporterId() {
        return tranporterId;
    }

    public void setTranporterId(Long tranporterId) {
        this.tranporterId = tranporterId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ownerRequestId == null) ? 0 : ownerRequestId.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((tranporterId == null) ? 0 : tranporterId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RequestCriteriaDTO other = (RequestCriteriaDTO) obj;
        if (ownerRequestId == null) {
            if (other.ownerRequestId != null)
                return false;
        } else if (!ownerRequestId.equals(other.ownerRequestId))
            return false;
        if (status != other.status)
            return false;
        if (tranporterId == null) {
            if (other.tranporterId != null)
                return false;
        } else if (!tranporterId.equals(other.tranporterId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RequestCriteriaDTO [ownerRequestId=" + ownerRequestId + ", status=" + status
                + ", tranporterId=" + tranporterId + "]";
    }

    public RequestCriteria toCriteria(){
        RequestCriteria criteria = new RequestCriteria();

        LongFilter ownerRequestIdFilter = new LongFilter();
        ownerRequestIdFilter.setEquals(ownerRequestId);
        criteria.setOwnerRequestId(ownerRequestIdFilter);

        StatusFilter statusFilter = new StatusFilter();
        statusFilter.setEquals(status);
        criteria.setStatus(statusFilter);

        LongFilter tranporterIdFilter = new LongFilter();
        tranporterIdFilter.setEquals(tranporterId);
        criteria.setTranporterId(tranporterIdFilter);

        return criteria;
    }

}