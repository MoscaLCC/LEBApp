package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Zone.
 */
@Entity
@Table(name = "zone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "zone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deliveryMen", "zone" }, allowSetters = true)
    private Set<Point> points = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_zone__transporters",
        joinColumns = @JoinColumn(name = "zone_id"),
        inverseJoinColumns = @JoinColumn(name = "transporters_id")
    )
    @JsonIgnoreProperties(value = { "ridePaths", "zones" }, allowSetters = true)
    private Set<Transporter> transporters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Zone id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Zone name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Point> getPoints() {
        return this.points;
    }

    public Zone points(Set<Point> points) {
        this.setPoints(points);
        return this;
    }

    public Zone addPoint(Point point) {
        this.points.add(point);
        point.setZone(this);
        return this;
    }

    public Zone removePoint(Point point) {
        this.points.remove(point);
        point.setZone(null);
        return this;
    }

    public void setPoints(Set<Point> points) {
        if (this.points != null) {
            this.points.forEach(i -> i.setZone(null));
        }
        if (points != null) {
            points.forEach(i -> i.setZone(this));
        }
        this.points = points;
    }

    public Set<Transporter> getTransporters() {
        return this.transporters;
    }

    public Zone transporters(Set<Transporter> transporters) {
        this.setTransporters(transporters);
        return this;
    }

    public Zone addTransporters(Transporter transporter) {
        this.transporters.add(transporter);
        transporter.getZones().add(this);
        return this;
    }

    public Zone removeTransporters(Transporter transporter) {
        this.transporters.remove(transporter);
        transporter.getZones().remove(this);
        return this;
    }

    public void setTransporters(Set<Transporter> transporters) {
        this.transporters = transporters;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zone)) {
            return false;
        }
        return id != null && id.equals(((Zone) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zone{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
