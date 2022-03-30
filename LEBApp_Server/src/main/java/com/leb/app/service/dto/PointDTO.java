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

    private Double lat;

    private Double lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getOwnerPoint() {
        return ownerPoint;
    }

    public void setOwnerPoint(Long ownerPoint) {
        this.ownerPoint = ownerPoint;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((closingTime == null) ? 0 : closingTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lat == null) ? 0 : lat.hashCode());
        result = prime * result + ((lng == null) ? 0 : lng.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((numberOfDeliveries == null) ? 0 : numberOfDeliveries.hashCode());
        result = prime * result + ((openingTime == null) ? 0 : openingTime.hashCode());
        result = prime * result + ((ownerPoint == null) ? 0 : ownerPoint.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PointDTO other = (PointDTO) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (closingTime == null) {
            if (other.closingTime != null)
                return false;
        } else if (!closingTime.equals(other.closingTime))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lat == null) {
            if (other.lat != null)
                return false;
        } else if (!lat.equals(other.lat))
            return false;
        if (lng == null) {
            if (other.lng != null)
                return false;
        } else if (!lng.equals(other.lng))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (numberOfDeliveries == null) {
            if (other.numberOfDeliveries != null)
                return false;
        } else if (!numberOfDeliveries.equals(other.numberOfDeliveries))
            return false;
        if (openingTime == null) {
            if (other.openingTime != null)
                return false;
        } else if (!openingTime.equals(other.openingTime))
            return false;
        if (ownerPoint == null) {
            if (other.ownerPoint != null)
                return false;
        } else if (!ownerPoint.equals(other.ownerPoint))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PointDTO [address=" + address + ", closingTime=" + closingTime + ", id=" + id + ", lat=" + lat
                + ", lng=" + lng + ", name=" + name + ", numberOfDeliveries=" + numberOfDeliveries + ", openingTime="
                + openingTime + ", ownerPoint=" + ownerPoint + ", status=" + status + "]";
    }

}
