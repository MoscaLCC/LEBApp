package com.leb.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RidePath.
 */
@Entity
@Table(name = "ride_path")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RidePath implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "distance")
    private String distance;

    @Column(name = "estimated_time")
    private String estimatedTime;

    @Column(name = "radius")
    private Double radius;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RidePath id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return this.source;
    }

    public RidePath source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public RidePath destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDistance() {
        return this.distance;
    }

    public RidePath distance(String distance) {
        this.setDistance(distance);
        return this;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedTime() {
        return this.estimatedTime;
    }

    public RidePath estimatedTime(String estimatedTime) {
        this.setEstimatedTime(estimatedTime);
        return this;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Double getRadius() {
        return this.radius;
    }

    public RidePath radius(Double radius) {
        this.setRadius(radius);
        return this;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RidePath)) {
            return false;
        }
        return id != null && id.equals(((RidePath) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RidePath{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
            ", distance='" + getDistance() + "'" +
            ", estimatedTime='" + getEstimatedTime() + "'" +
            ", radius=" + getRadius() +
            "}";
    }
}
