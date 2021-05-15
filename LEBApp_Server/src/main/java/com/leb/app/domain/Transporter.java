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
 * A Transporter.
 */
@Entity
@Table(name = "transporter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transporter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "favourite_transport")
    private String favouriteTransport;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_transporter__ride_path",
        joinColumns = @JoinColumn(name = "transporter_id"),
        inverseJoinColumns = @JoinColumn(name = "ride_path_id")
    )
    @JsonIgnoreProperties(value = { "requests", "transports" }, allowSetters = true)
    private Set<RidePath> ridePaths = new HashSet<>();

    @ManyToMany(mappedBy = "transporters")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "points", "transporters" }, allowSetters = true)
    private Set<Zone> zones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transporter id(Long id) {
        this.id = id;
        return this;
    }

    public String getFavouriteTransport() {
        return this.favouriteTransport;
    }

    public Transporter favouriteTransport(String favouriteTransport) {
        this.favouriteTransport = favouriteTransport;
        return this;
    }

    public void setFavouriteTransport(String favouriteTransport) {
        this.favouriteTransport = favouriteTransport;
    }

    public Integer getNumberOfDeliveries() {
        return this.numberOfDeliveries;
    }

    public Transporter numberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
        return this;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getNumberOfKm() {
        return this.numberOfKm;
    }

    public Transporter numberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
        return this;
    }

    public void setNumberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public Double getReceivedValue() {
        return this.receivedValue;
    }

    public Transporter receivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
        return this;
    }

    public void setReceivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
    }

    public Double getValueToReceive() {
        return this.valueToReceive;
    }

    public Transporter valueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
        return this;
    }

    public void setValueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
    }

    public Double getRanking() {
        return this.ranking;
    }

    public Transporter ranking(Double ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public Transporter userInfo(UserInfo userInfo) {
        this.setUserInfo(userInfo);
        return this;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Set<RidePath> getRidePaths() {
        return this.ridePaths;
    }

    public Transporter ridePaths(Set<RidePath> ridePaths) {
        this.setRidePaths(ridePaths);
        return this;
    }

    public Transporter addRidePath(RidePath ridePath) {
        this.ridePaths.add(ridePath);
        ridePath.getTransports().add(this);
        return this;
    }

    public Transporter removeRidePath(RidePath ridePath) {
        this.ridePaths.remove(ridePath);
        ridePath.getTransports().remove(this);
        return this;
    }

    public void setRidePaths(Set<RidePath> ridePaths) {
        this.ridePaths = ridePaths;
    }

    public Set<Zone> getZones() {
        return this.zones;
    }

    public Transporter zones(Set<Zone> zones) {
        this.setZones(zones);
        return this;
    }

    public Transporter addZones(Zone zone) {
        this.zones.add(zone);
        zone.getTransporters().add(this);
        return this;
    }

    public Transporter removeZones(Zone zone) {
        this.zones.remove(zone);
        zone.getTransporters().remove(this);
        return this;
    }

    public void setZones(Set<Zone> zones) {
        if (this.zones != null) {
            this.zones.forEach(i -> i.removeTransporters(this));
        }
        if (zones != null) {
            zones.forEach(i -> i.addTransporters(this));
        }
        this.zones = zones;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transporter)) {
            return false;
        }
        return id != null && id.equals(((Transporter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transporter{" +
            "id=" + getId() +
            ", favouriteTransport='" + getFavouriteTransport() + "'" +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", numberOfKm=" + getNumberOfKm() +
            ", receivedValue=" + getReceivedValue() +
            ", valueToReceive=" + getValueToReceive() +
            ", ranking=" + getRanking() +
            "}";
    }
}
