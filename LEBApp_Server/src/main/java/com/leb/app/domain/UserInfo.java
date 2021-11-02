package com.leb.app.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserInfo.
 */
@Entity
@Table(name = "user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nib")
    private String nib;

    @Column(name = "nif")
    private Integer nif;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "link_social")
    private String linkSocial;

    @Column(name = "number_requests")
    private Integer numberRequests;

    @Column(name = "payed_value")
    private Double payedValue;

    @Column(name = "value_to_pay")
    private Double valueToPay;

    @Column(name = "ranking")
    private Double ranking;

    @Column(name = "number_of_deliveries")
    private Integer numberOfDeliveries;

    @Column(name = "number_of_km")
    private Double numberOfKm;

    @OneToMany(mappedBy = "ownerRequest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerRequest", "tranporter" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "tranporter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerRequest", "tranporter" }, allowSetters = true)
    private Set<Request> transportations = new HashSet<>();

    @OneToMany(mappedBy = "ownerPoint")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerPoint" }, allowSetters = true)
    private Set<Point> points = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public UserInfo phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNib() {
        return this.nib;
    }

    public UserInfo nib(String nib) {
        this.setNib(nib);
        return this;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public Integer getNif() {
        return this.nif;
    }

    public UserInfo nif(Integer nif) {
        this.setNif(nif);
        return this;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public Instant getBirthday() {
        return this.birthday;
    }

    public UserInfo birthday(Instant birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return this.address;
    }

    public UserInfo address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkSocial() {
        return this.linkSocial;
    }

    public UserInfo linkSocial(String linkSocial) {
        this.setLinkSocial(linkSocial);
        return this;
    }

    public void setLinkSocial(String linkSocial) {
        this.linkSocial = linkSocial;
    }

    public Integer getNumberRequests() {
        return this.numberRequests;
    }

    public UserInfo numberRequests(Integer numberRequests) {
        this.setNumberRequests(numberRequests);
        return this;
    }

    public void setNumberRequests(Integer numberRequests) {
        this.numberRequests = numberRequests;
    }

    public Double getPayedValue() {
        return this.payedValue;
    }

    public UserInfo payedValue(Double payedValue) {
        this.setPayedValue(payedValue);
        return this;
    }

    public void setPayedValue(Double payedValue) {
        this.payedValue = payedValue;
    }

    public Double getValueToPay() {
        return this.valueToPay;
    }

    public UserInfo valueToPay(Double valueToPay) {
        this.setValueToPay(valueToPay);
        return this;
    }

    public void setValueToPay(Double valueToPay) {
        this.valueToPay = valueToPay;
    }

    public Double getRanking() {
        return this.ranking;
    }

    public UserInfo ranking(Double ranking) {
        this.setRanking(ranking);
        return this;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public Integer getNumberOfDeliveries() {
        return this.numberOfDeliveries;
    }

    public UserInfo numberOfDeliveries(Integer numberOfDeliveries) {
        this.setNumberOfDeliveries(numberOfDeliveries);
        return this;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getNumberOfKm() {
        return this.numberOfKm;
    }

    public UserInfo numberOfKm(Double numberOfKm) {
        this.setNumberOfKm(numberOfKm);
        return this;
    }

    public void setNumberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setOwnerRequest(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setOwnerRequest(this));
        }
        this.requests = requests;
    }

    public UserInfo requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public UserInfo addRequests(Request request) {
        this.requests.add(request);
        request.setOwnerRequest(this);
        return this;
    }

    public UserInfo removeRequests(Request request) {
        this.requests.remove(request);
        request.setOwnerRequest(null);
        return this;
    }

    public Set<Request> getTransportations() {
        return this.transportations;
    }

    public void setTransportations(Set<Request> requests) {
        if (this.transportations != null) {
            this.transportations.forEach(i -> i.setTranporter(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setTranporter(this));
        }
        this.transportations = requests;
    }

    public UserInfo transportations(Set<Request> requests) {
        this.setTransportations(requests);
        return this;
    }

    public UserInfo addTransportations(Request request) {
        this.transportations.add(request);
        request.setTranporter(this);
        return this;
    }

    public UserInfo removeTransportations(Request request) {
        this.transportations.remove(request);
        request.setTranporter(null);
        return this;
    }

    public Set<Point> getPoints() {
        return this.points;
    }

    public void setPoints(Set<Point> points) {
        if (this.points != null) {
            this.points.forEach(i -> i.setOwnerPoint(null));
        }
        if (points != null) {
            points.forEach(i -> i.setOwnerPoint(this));
        }
        this.points = points;
    }

    public UserInfo points(Set<Point> points) {
        this.setPoints(points);
        return this;
    }

    public UserInfo addPoint(Point point) {
        this.points.add(point);
        point.setOwnerPoint(this);
        return this;
    }

    public UserInfo removePoint(Point point) {
        this.points.remove(point);
        point.setOwnerPoint(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nib='" + getNib() + "'" +
            ", nif=" + getNif() +
            ", birthday='" + getBirthday() + "'" +
            ", address='" + getAddress() + "'" +
            ", linkSocial='" + getLinkSocial() + "'" +
            ", numberRequests=" + getNumberRequests() +
            ", payedValue=" + getPayedValue() +
            ", valueToPay=" + getValueToPay() +
            ", ranking=" + getRanking() +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", numberOfKm=" + getNumberOfKm() +
            "}";
    }
}
