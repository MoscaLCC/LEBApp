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

    private String status;

    private int ownerRequestId;

    private int transporterId;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOwnerRequestId() {
        return ownerRequestId;
    }

    public void setOwnerRequestId(int ownerRequestId) {
        this.ownerRequestId = ownerRequestId;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(int transporterId) {
        this.transporterId = transporterId;
    }

    @Override
    public String toString() {
        return "RequestCriteriaDTO [ownerRequestId=" + ownerRequestId + ", status=" + status
                + ", transporterId=" + transporterId + "]";
    }

    public RequestCriteria toCriteria(){
        RequestCriteria criteria = new RequestCriteria();

        LongFilter ownerRequestIdFilter = new LongFilter();
        ownerRequestIdFilter.setEquals(Long.valueOf(ownerRequestId));
        criteria.setOwnerRequestId(ownerRequestIdFilter);

        StatusFilter statusFilter = new StatusFilter();
        switch (status){
            case "Available":
                statusFilter.setEquals(Status.WAITING_COLLECTION);
                break;
            default:
                statusFilter.setEquals(Status.WAITING_COLLECTION);
                break;
        }
        criteria.setStatus(statusFilter);

        LongFilter transporterIdFilter = new LongFilter();
        transporterIdFilter.setEquals(Long.valueOf(transporterId));
        criteria.setTransporterId(transporterIdFilter);

        return criteria;
    }

}