package com.leb.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.RidePath} entity.
 */
public class RidePathDTO implements Serializable {

    private Long id;

    private String source;

    private String destination;

    private String distance;

    private String estimatedTime;

    private Double radius;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RidePathDTO)) {
            return false;
        }

        RidePathDTO ridePathDTO = (RidePathDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ridePathDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RidePathDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
            ", distance='" + getDistance() + "'" +
            ", estimatedTime='" + getEstimatedTime() + "'" +
            ", radius=" + getRadius() +
            "}";
    }
}
