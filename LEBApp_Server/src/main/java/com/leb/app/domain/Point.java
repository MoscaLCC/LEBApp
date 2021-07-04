package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Point.
 */
@Entity
@Table(name = "point")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;

    @Column(name = "number_of_deliveries")
    private Integer numberOfDeliveries;

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

    @OneToMany(mappedBy = "point")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "point" }, allowSetters = true)
    private Set<DeliveryMan> deliveryMen = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "points", "transporters" }, allowSetters = true)
    private Zone zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point id(Long id) {
        this.id = id;
        return this;
    }

    public String getOpeningTime() {
        return this.openingTime;
    }

    public Point openingTime(String openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public Integer getNumberOfDeliveries() {
        return this.numberOfDeliveries;
    }

    public Point numberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
        return this;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getReceivedValue() {
        return this.receivedValue;
    }

    public Point receivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
        return this;
    }

    public void setReceivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
    }

    public Double getValueToReceive() {
        return this.valueToReceive;
    }

    public Point valueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
        return this;
    }

    public void setValueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
    }

    public Double getRanking() {
        return this.ranking;
    }

    public Point ranking(Double ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public Point userInfo(UserInfo userInfo) {
        this.setUserInfo(userInfo);
        return this;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Set<DeliveryMan> getDeliveryMen() {
        return this.deliveryMen;
    }

    public Point deliveryMen(Set<DeliveryMan> deliveryMen) {
        this.setDeliveryMen(deliveryMen);
        return this;
    }

    public Point addDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMen.add(deliveryMan);
        deliveryMan.setPoint(this);
        return this;
    }

    public Point removeDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMen.remove(deliveryMan);
        deliveryMan.setPoint(null);
        return this;
    }

    public void setDeliveryMen(Set<DeliveryMan> deliveryMen) {
        if (this.deliveryMen != null) {
            this.deliveryMen.forEach(i -> i.setPoint(null));
        }
        if (deliveryMen != null) {
            deliveryMen.forEach(i -> i.setPoint(this));
        }
        this.deliveryMen = deliveryMen;
    }

    public Zone getZone() {
        return this.zone;
    }

    public Point zone(Zone zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        return id != null && id.equals(((Point) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Point [closingTime=" + closingTime + ", deliveryMen=" + deliveryMen + ", id=" + id
                + ", numberOfDeliveries=" + numberOfDeliveries + ", openingTime=" + openingTime + ", ranking=" + ranking
                + ", receivedValue=" + receivedValue + ", userInfo=" + userInfo + ", valueToReceive=" + valueToReceive
                + ", zone=" + zone + "]";
    }
}
