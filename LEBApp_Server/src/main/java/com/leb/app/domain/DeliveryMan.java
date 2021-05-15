package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeliveryMan.
 */
@Entity
@Table(name = "delivery_man")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryMan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "number_of_deliveries")
    private Integer numberOfDeliveries;

    @Column(name = "number_of_km")
    private Double numberOfKm;

    @Column(name = "received_value")
    private Double receivedValue;

    @Column(name = "value_to_receive")
    private Double valueToReceive;

    @Column(name = "ranking")
    private Double ranking;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private UserInfo userInfo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "userInfo", "deliveryMen", "zone" }, allowSetters = true)
    private Point point;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryMan id(Long id) {
        this.id = id;
        return this;
    }

    public String getOpeningTime() {
        return this.openingTime;
    }

    public DeliveryMan openingTime(String openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public Integer getNumberOfDeliveries() {
        return this.numberOfDeliveries;
    }

    public DeliveryMan numberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
        return this;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getNumberOfKm() {
        return this.numberOfKm;
    }

    public DeliveryMan numberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
        return this;
    }

    public void setNumberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public Double getReceivedValue() {
        return this.receivedValue;
    }

    public DeliveryMan receivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
        return this;
    }

    public void setReceivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
    }

    public Double getValueToReceive() {
        return this.valueToReceive;
    }

    public DeliveryMan valueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
        return this;
    }

    public void setValueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
    }

    public Double getRanking() {
        return this.ranking;
    }

    public DeliveryMan ranking(Double ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public DeliveryMan userInfo(UserInfo userInfo) {
        this.setUserInfo(userInfo);
        return this;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Point getPoint() {
        return this.point;
    }

    public DeliveryMan point(Point point) {
        this.setPoint(point);
        return this;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryMan)) {
            return false;
        }
        return id != null && id.equals(((DeliveryMan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryMan{" +
            "id=" + getId() +
            ", openingTime='" + getOpeningTime() + "'" +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", numberOfKm=" + getNumberOfKm() +
            ", receivedValue=" + getReceivedValue() +
            ", valueToReceive=" + getValueToReceive() +
            ", ranking=" + getRanking() +
            "}";
    }
}
