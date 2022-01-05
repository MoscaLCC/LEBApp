package com.leb.app.domain;

import com.leb.app.domain.enumeration.Status;
import java.io.Serializable;
import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "product_value")
    private Double productValue;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "destination_contact_mobile")
    private String destinationContactMobile;

    @Column(name = "destination_contact_email")
    private String destinationContactEmail;

    @Column(name = "init_date")
    private String initDate;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "special_characteristics")
    private String specialCharacteristics;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "hight")
    private Double hight;

    @Column(name = "width")
    private Double width;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "shipping_costs")
    private Double shippingCosts;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "owner_request")
    private Long ownerRequest;

    @Column(name = "transporter")
    private Long transporter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Request id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProductValue() {
        return this.productValue;
    }

    public Request productValue(Double productValue) {
        this.setProductValue(productValue);
        return this;
    }

    public void setProductValue(Double productValue) {
        this.productValue = productValue;
    }

    public String getProductName() {
        return this.productName;
    }

    public Request productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSource() {
        return this.source;
    }

    public Request source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public Request destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getInitDate() {
        return this.initDate;
    }

    public Request initDate(String initDate) {
        this.setInitDate(initDate);
        return this;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public Request expirationDate(String expirationDate) {
        this.setExpirationDate(expirationDate);
        return this;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSpecialCharacteristics() {
        return this.specialCharacteristics;
    }

    public Request specialCharacteristics(String specialCharacteristics) {
        this.setSpecialCharacteristics(specialCharacteristics);
        return this;
    }

    public void setSpecialCharacteristics(String specialCharacteristics) {
        this.specialCharacteristics = specialCharacteristics;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Request weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHight() {
        return this.hight;
    }

    public Request hight(Double hight) {
        this.setHight(hight);
        return this;
    }

    public void setHight(Double hight) {
        this.hight = hight;
    }

    public Double getWidth() {
        return this.width;
    }

    public Request width(Double width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Status getStatus() {
        return this.status;
    }

    public Request status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getShippingCosts() {
        return this.shippingCosts;
    }

    public Request shippingCosts(Double shippingCosts) {
        this.setShippingCosts(shippingCosts);
        return this;
    }

    public void setShippingCosts(Double shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    public Double getRating() {
        return this.rating;
    }

    public Request rating(Double rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getOwnerRequest() {
        return this.ownerRequest;
    }

    public void setOwnerRequest(Long userInfo) {
        this.ownerRequest = userInfo;
    }

    public Request ownerRequest(Long userInfo) {
        this.setOwnerRequest(userInfo);
        return this;
    }

    public String getDestinationContactMobile() {
        return destinationContactMobile;
    }

    public void setDestinationContactMobile(String destinationContactMobile) {
        this.destinationContactMobile = destinationContactMobile;
    }

    public String getDestinationContactEmail() {
        return destinationContactEmail;
    }

    public void setDestinationContactEmail(String destinationContactEmail) {
        this.destinationContactEmail = destinationContactEmail;
    }

    public Long getTransporter() {
        return this.transporter;
    }

    public void setTransporter(Long userInfo) {
        this.transporter = userInfo;
    }

    public Request transporter(Long userInfo) {
        this.setTransporter(userInfo);
        return this;
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
            ", initDate='" + getInitDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", specialCharacteristics='" + getSpecialCharacteristics() + "'" +
            ", weight=" + getWeight() +
            ", hight=" + getHight() +
            ", width=" + getWidth() +
            ", status='" + getStatus() + "'" +
            ", shippingCosts=" + getShippingCosts() +
            ", rating=" + getRating() +
            ", destinationContactEmail=" + getDestinationContactEmail() +
            ", rating=" + getDestinationContactMobile() +
            "}";
    }
}
