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
 * A Producer.
 */
@Entity
@Table(name = "producer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nib")
    private String nib;

    @Column(name = "nif")
    private Integer nif;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "adress")
    private String adress;

    @Column(name = "photo")
    private String photo;

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

    @OneToMany(mappedBy = "producer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dimensions", "route", "producer" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producer id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Producer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return this.mail;
    }

    public Producer mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Producer phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNib() {
        return this.nib;
    }

    public Producer nib(String nib) {
        this.nib = nib;
        return this;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public Integer getNif() {
        return this.nif;
    }

    public Producer nif(Integer nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Producer birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAdress() {
        return this.adress;
    }

    public Producer adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Producer photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLinkSocial() {
        return this.linkSocial;
    }

    public Producer linkSocial(String linkSocial) {
        this.linkSocial = linkSocial;
        return this;
    }

    public void setLinkSocial(String linkSocial) {
        this.linkSocial = linkSocial;
    }

    public Integer getNumberRequests() {
        return this.numberRequests;
    }

    public Producer numberRequests(Integer numberRequests) {
        this.numberRequests = numberRequests;
        return this;
    }

    public void setNumberRequests(Integer numberRequests) {
        this.numberRequests = numberRequests;
    }

    public Double getPayedValue() {
        return this.payedValue;
    }

    public Producer payedValue(Double payedValue) {
        this.payedValue = payedValue;
        return this;
    }

    public void setPayedValue(Double payedValue) {
        this.payedValue = payedValue;
    }

    public Double getValueToPay() {
        return this.valueToPay;
    }

    public Producer valueToPay(Double valueToPay) {
        this.valueToPay = valueToPay;
        return this;
    }

    public void setValueToPay(Double valueToPay) {
        this.valueToPay = valueToPay;
    }

    public Double getRanking() {
        return this.ranking;
    }

    public Producer ranking(Double ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public Producer requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public Producer addRequest(Request request) {
        this.requests.add(request);
        request.setProducer(this);
        return this;
    }

    public Producer removeRequest(Request request) {
        this.requests.remove(request);
        request.setProducer(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setProducer(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setProducer(this));
        }
        this.requests = requests;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producer)) {
            return false;
        }
        return id != null && id.equals(((Producer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mail='" + getMail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nib='" + getNib() + "'" +
            ", nif=" + getNif() +
            ", birthday='" + getBirthday() + "'" +
            ", adress='" + getAdress() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", linkSocial='" + getLinkSocial() + "'" +
            ", numberRequests=" + getNumberRequests() +
            ", payedValue=" + getPayedValue() +
            ", valueToPay=" + getValueToPay() +
            ", ranking=" + getRanking() +
            "}";
    }
}
