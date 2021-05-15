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

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "producer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dimensions", "ridePath", "producer" }, allowSetters = true)
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

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public Producer userInfo(UserInfo userInfo) {
        this.setUserInfo(userInfo);
        return this;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
            ", linkSocial='" + getLinkSocial() + "'" +
            ", numberRequests=" + getNumberRequests() +
            ", payedValue=" + getPayedValue() +
            ", valueToPay=" + getValueToPay() +
            ", ranking=" + getRanking() +
            "}";
    }
}
