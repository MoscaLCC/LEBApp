package com.leb.app.service.dto;

import com.leb.app.domain.enumeration.Status;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.criteria.StatusFilter;

import tech.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;


public class RequestCriteriaDTO implements Serializable {

    private String status;

    private Integer ownerRequest;

    private Integer transporter;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOwnerRequest() {
        return ownerRequest;
    }

    public void setOwnerRequest(Integer ownerRequest) {
        this.ownerRequest = ownerRequest;
    }

    public Integer getTransporter() {
        return transporter;
    }

    public void setTransporter(Integer transporter) {
        this.transporter = transporter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerRequest + transporter);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDTO{" +
            "status='" + getStatus() + "'" +
            ", ownerRequest=" + getOwnerRequest() +
            ", transporter=" + getTransporter() +
            "}";
    }
    
    public RequestCriteria toCriteria(){
        RequestCriteria criteria = new RequestCriteria();

        LongFilter ownerRequestIdFilter = new LongFilter();
        ownerRequestIdFilter.setEquals(Long.valueOf(ownerRequest));
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
        transporterIdFilter.setEquals(Long.valueOf(transporter));
        criteria.setTransporterId(transporterIdFilter);

        return criteria;
    }
}
