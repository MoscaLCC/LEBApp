package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leb.app.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_value")
    private Double productValue;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "destination_contact")
    private String destinationContact;

    @Column(name = "init_date")
    private Instant initDate;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "special_characteristics")
    private String specialCharacteristics;

    @Column(name = "product_weight")
    private Double productWeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "estimated_date")
    private Instant estimatedDate;

    @Column(name = "delivery_time")
    private Instant deliveryTime;

    @Column(name = "shipping_costs")
    private Double shippingCosts;

    @Column(name = "rating")
    private Double rating;

    @JsonIgnoreProperties(value = { "request" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Dimensions dimensions;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "requests", "transports" }, allowSetters = true)
    private RidePath ridePath;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "requests" }, allowSetters = true)
    private Producer producer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request id(Long id) {
        this.id = id;
        return this;
    }

    public Double getProductValue() {
        return this.productValue;
    }

    public Request productValue(Double productValue) {
        this.productValue = productValue;
        return this;
    }

    public void setProductValue(Double productValue) {
        this.productValue = productValue;
    }

    public String getProductName() {
        return this.productName;
    }

    public Request productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSource() {
        return this.source;
    }

    public Request source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public Request destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationContact() {
        return this.destinationContact;
    }

    public Request destinationContact(String destinationContact) {
        this.destinationContact = destinationContact;
        return this;
    }

    public void setDestinationContact(String destinationContact) {
        this.destinationContact = destinationContact;
    }

    public Instant getInitDate() {
        return this.initDate;
    }

    public Request initDate(Instant initDate) {
        this.initDate = initDate;
        return this;
    }

    public void setInitDate(Instant initDate) {
        this.initDate = initDate;
    }

    public Instant getExpirationDate() {
        return this.expirationDate;
    }

    public Request expirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return this.description;
    }

    public Request description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialCharacteristics() {
        return this.specialCharacteristics;
    }

    public Request specialCharacteristics(String specialCharacteristics) {
        this.specialCharacteristics = specialCharacteristics;
        return this;
    }

    public void setSpecialCharacteristics(String specialCharacteristics) {
        this.specialCharacteristics = specialCharacteristics;
    }

    public Double getProductWeight() {
        return this.productWeight;
    }

    public Request productWeight(Double productWeight) {
        this.productWeight = productWeight;
        return this;
    }

    public void setProductWeight(Double productWeight) {
        this.productWeight = productWeight;
    }

    public Status getStatus() {
        return this.status;
    }

    public Request status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getEstimatedDate() {
        return this.estimatedDate;
    }

    public Request estimatedDate(Instant estimatedDate) {
        this.estimatedDate = estimatedDate;
        return this;
    }

    public void setEstimatedDate(Instant estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public Instant getDeliveryTime() {
        return this.deliveryTime;
    }

    public Request deliveryTime(Instant deliveryTime) {
        this.deliveryTime = deliveryTime;
        return this;
    }

    public void setDeliveryTime(Instant deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Double getShippingCosts() {
        return this.shippingCosts;
    }

    public Request shippingCosts(Double shippingCosts) {
        this.shippingCosts = shippingCosts;
        return this;
    }

    public void setShippingCosts(Double shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    public Double getRating() {
        return this.rating;
    }

    public Request rating(Double rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Dimensions getDimensions() {
        return this.dimensions;
    }

    public Request dimensions(Dimensions dimensions) {
        this.setDimensions(dimensions);
        return this;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public RidePath getRidePath() {
        return this.ridePath;
    }

    public Request ridePath(RidePath ridePath) {
        this.setRidePath(ridePath);
        return this;
    }

    public void setRidePath(RidePath ridePath) {
        this.ridePath = ridePath;
    }

    public Producer getProducer() {
        return this.producer;
    }

    public Request producer(Producer producer) {
        this.setProducer(producer);
        return this;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
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
            "}";
    }
}
