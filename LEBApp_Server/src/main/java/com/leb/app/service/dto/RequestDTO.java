package com.leb.app.service.dto;

import com.leb.app.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
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

    private Instant initDate;

    private Instant expirationDate;

    private String description;

    private String specialCharacteristics;

    private Double productWeight;

    private Status status;

    private Instant estimatedDate;

    private Instant deliveryTime;

    private Double shippingCosts;

    private Double rating;

    private DimensionsDTO dimensions;

    private RidePathDTO ridePath;

    private ProducerDTO producer;

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

    public Instant getInitDate() {
        return initDate;
    }

    public void setInitDate(Instant initDate) {
        this.initDate = initDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
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

    public Double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Double productWeight) {
        this.productWeight = productWeight;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(Instant estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public Instant getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Instant deliveryTime) {
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

    public DimensionsDTO getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsDTO dimensions) {
        this.dimensions = dimensions;
    }

    public RidePathDTO getRidePath() {
        return ridePath;
    }

    public void setRidePath(RidePathDTO ridePath) {
        this.ridePath = ridePath;
    }

    public ProducerDTO getProducer() {
        return producer;
    }

    public void setProducer(ProducerDTO producer) {
        this.producer = producer;
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
            ", productWeight=" + getProductWeight() +
            ", status='" + getStatus() + "'" +
            ", estimatedDate='" + getEstimatedDate() + "'" +
            ", deliveryTime='" + getDeliveryTime() + "'" +
            ", shippingCosts=" + getShippingCosts() +
            ", rating=" + getRating() +
            ", dimensions=" + getDimensions() +
            ", ridePath=" + getRidePath() +
            ", producer=" + getProducer() +
            "}";
    }
}
