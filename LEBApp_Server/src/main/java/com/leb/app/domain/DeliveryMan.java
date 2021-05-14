package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeliveryMan.
 */
@Entity
@Table(name = "delivery_man")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryMan implements Serializable {

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

    @Column(name = "nif")
    private Integer nif;

    @Column(name = "nib")
    private String nib;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "photo")
    private String photo;

    @Column(name = "opening_time")
    private String openingTime;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "deliveryMen", "zone" }, allowSetters = true)
    private Point point;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryMan id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public DeliveryMan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public DeliveryMan email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public DeliveryMan phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNif() {
        return this.nif;
    }

    public DeliveryMan nif(Integer nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public String getNib() {
        return this.nib;
    }

    public DeliveryMan nib(String nib) {
        this.nib = nib;
        return this;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public DeliveryMan birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return this.address;
    }

    public DeliveryMan address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return this.photo;
    }

    public DeliveryMan photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOpeningTime() {
        return this.openingTime;
    }

    public DeliveryMan openingTime(String openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public Integer getNumberOfDeliveries() {
        return this.numberOfDeliveries;
    }

    public DeliveryMan numberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
        return this;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getNumberOfKm() {
        return this.numberOfKm;
    }

    public DeliveryMan numberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
        return this;
    }

    public void setNumberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public Double getReceivedValue() {
        return this.receivedValue;
    }

    public DeliveryMan receivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
        return this;
    }

    public void setReceivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
    }

    public Double getValueToReceive() {
        return this.valueToReceive;
    }

    public DeliveryMan valueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
        return this;
    }

    public void setValueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
    }

    public Double getRanking() {
        return this.ranking;
    }

    public DeliveryMan ranking(Double ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public Point getPoint() {
        return this.point;
    }

    public DeliveryMan point(Point point) {
        this.setPoint(point);
        return this;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryMan)) {
            return false;
        }
        return id != null && id.equals(((DeliveryMan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryMan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nif=" + getNif() +
            ", nib='" + getNib() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", address='" + getAddress() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", openingTime='" + getOpeningTime() + "'" +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", numberOfKm=" + getNumberOfKm() +
            ", receivedValue=" + getReceivedValue() +
            ", valueToReceive=" + getValueToReceive() +
            ", ranking=" + getRanking() +
            "}";
    }
}
