package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
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

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nib")
    private String nib;

    @Column(name = "nif")
    private Integer nif;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "photo")
    private String photo;

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

    public String getName() {
        return this.name;
    }

    public Transporter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Transporter email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Transporter phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNib() {
        return this.nib;
    }

    public Transporter nib(String nib) {
        this.nib = nib;
        return this;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public Integer getNif() {
        return this.nif;
    }

    public Transporter nif(Integer nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Transporter birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return this.address;
    }

    public Transporter address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Transporter photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nib='" + getNib() + "'" +
            ", nif=" + getNif() +
            ", birthday='" + getBirthday() + "'" +
            ", address='" + getAddress() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", favouriteTransport='" + getFavouriteTransport() + "'" +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", numberOfKm=" + getNumberOfKm() +
            ", receivedValue=" + getReceivedValue() +
            ", valueToReceive=" + getValueToReceive() +
            ", ranking=" + getRanking() +
            "}";
    }
}
