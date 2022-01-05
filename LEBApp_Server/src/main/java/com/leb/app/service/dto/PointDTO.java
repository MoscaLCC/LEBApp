package com.leb.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.Point} entity.
 */
public class PointDTO implements Serializable {

    private Long id;

    private String name;

    private String openingTime;

    private String closingTime;

    private String address;

    private Integer numberOfDeliveries;

    private Long ownerPoint;

    private Long status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getOwnerPoint() {
        return ownerPoint;
    }

    public void setOwnerPoint(Long ownerPoint) {
        this.ownerPoint = ownerPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointDTO)) {
            return false;
        }

        PointDTO pointDTO = (PointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", openingTime='" + getOpeningTime() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            ", address='" + getAddress() + "'" +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", status=" + getStatus() +
            ", ownerPoint=" + getOwnerPoint() +
            "}";
    }
}
