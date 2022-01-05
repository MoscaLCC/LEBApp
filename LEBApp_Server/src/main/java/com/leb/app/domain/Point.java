package com.leb.app.domain;

import java.io.Serializable;
import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;

    @Column(name = "address")
    private String address;

    @Column(name = "number_of_deliveries")
    private Integer numberOfDeliveries;

    @Column(name = "owner_point")
    private Long ownerPoint;

    @Column(name = "status")
    private Long status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Point id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpeningTime() {
        return this.openingTime;
    }

    public Point openingTime(String openingTime) {
        this.setOpeningTime(openingTime);
        return this;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return this.closingTime;
    }

    public Point closingTime(String closingTime) {
        this.setClosingTime(closingTime);
        return this;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getAddress() {
        return this.address;
    }

    public Point address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfDeliveries() {
        return this.numberOfDeliveries;
    }

    public Point numberOfDeliveries(Integer numberOfDeliveries) {
        this.setNumberOfDeliveries(numberOfDeliveries);
        return this;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Long getOwnerPoint() {
        return this.ownerPoint;
    }

    public void setOwnerPoint(Long userInfo) {
        this.ownerPoint = userInfo;
    }

    public Point ownerPoint(Long userInfo) {
        this.setOwnerPoint(userInfo);
        return this;
    }

    

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

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

    // prettier-ignore
    @Override
    public String toString() {
        return "Point{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", openingTime='" + getOpeningTime() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            ", address='" + getAddress() + "'" +
            ", status=" + getStatus() +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            "}";
    }
}
