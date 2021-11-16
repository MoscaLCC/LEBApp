package com.leb.app.service.dto;

import com.leb.app.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.Request} entity.
 */
public class RequestDTO implements Serializable {

    private Long id;

    private Double productValue;

    private String productName;

    private String source;

    private String destination;

    private String destinationContact;

    private String initDate;

    private String expirationDate;

    private String description;

    private String specialCharacteristics;

    private Double weight;

    private Double hight;

    private Double width;

    private Status status;

    private String estimatedDate;

    private String deliveryTime;

    private Double shippingCosts;

    private Double rating;

    private Long ownerRequest;

    private Long tranporter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProductValue() {
        return productValue;
    }

    public void setProductValue(Double productValue) {
        this.productValue = productValue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationContact() {
        return destinationContact;
    }

    public void setDestinationContact(String destinationContact) {
        this.destinationContact = destinationContact;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialCharacteristics() {
        return specialCharacteristics;
    }

    public void setSpecialCharacteristics(String specialCharacteristics) {
        this.specialCharacteristics = specialCharacteristics;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHight() {
        return hight;
    }

    public void setHight(Double hight) {
        this.hight = hight;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Double getShippingCosts() {
        return shippingCosts;
    }

    public void setShippingCosts(Double shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getOwnerRequest() {
        return ownerRequest;
    }

    public void setOwnerRequest(Long ownerRequest) {
        this.ownerRequest = ownerRequest;
    }

    public Long getTranporter() {
        return tranporter;
    }

    public void setTranporter(Long tranporter) {
        this.tranporter = tranporter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", productValue=" + getProductValue() +
            ", productName='" + getProductName() + "'" +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
            ", destinationContact='" + getDestinationContact() + "'" +
            ", initDate='" + getInitDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", specialCharacteristics='" + getSpecialCharacteristics() + "'" +
            ", weight=" + getWeight() +
            ", hight=" + getHight() +
            ", width=" + getWidth() +
            ", status='" + getStatus() + "'" +
            ", estimatedDate='" + getEstimatedDate() + "'" +
            ", deliveryTime='" + getDeliveryTime() + "'" +
            ", shippingCosts=" + getShippingCosts() +
            ", rating=" + getRating() +
            ", ownerRequest=" + getOwnerRequest() +
            ", tranporter=" + getTranporter() +
            "}";
    }
}
