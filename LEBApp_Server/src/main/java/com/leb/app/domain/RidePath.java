package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "distance")
    private String distance;

    @Column(name = "estimated_time")
    private String estimatedTime;

    @OneToMany(mappedBy = "ridePath")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dimensions", "ridePath", "producer" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();

    @ManyToMany(mappedBy = "ridePaths")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "ridePaths", "zones" }, allowSetters = true)
    private Set<Transporter> transports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RidePath id(Long id) {
        this.id = id;
        return this;
    }

    public String getSource() {
        return this.source;
    }

    public RidePath source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public RidePath destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDistance() {
        return this.distance;
    }

    public RidePath distance(String distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedTime() {
        return this.estimatedTime;
    }

    public RidePath estimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public RidePath requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public RidePath addRequest(Request request) {
        this.requests.add(request);
        request.setRidePath(this);
        return this;
    }

    public RidePath removeRequest(Request request) {
        this.requests.remove(request);
        request.setRidePath(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setRidePath(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setRidePath(this));
        }
        this.requests = requests;
    }

    public Set<Transporter> getTransports() {
        return this.transports;
    }

    public RidePath transports(Set<Transporter> transporters) {
        this.setTransports(transporters);
        return this;
    }

    public RidePath addTransports(Transporter transporter) {
        this.transports.add(transporter);
        transporter.getRidePaths().add(this);
        return this;
    }

    public RidePath removeTransports(Transporter transporter) {
        this.transports.remove(transporter);
        transporter.getRidePaths().remove(this);
        return this;
    }

    public void setTransports(Set<Transporter> transporters) {
        if (this.transports != null) {
            this.transports.forEach(i -> i.removeRidePath(this));
        }
        if (transporters != null) {
            transporters.forEach(i -> i.addRidePath(this));
        }
        this.transports = transporters;
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
            "}";
    }
}
