package com.leb.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.UserInfo} entity.
 */
public class UserInfoDTO implements Serializable {

    private Long id;

    private String phoneNumber;

    private String nib;

    private Integer nif;

    private Instant birthday;

    private String address;

    private String linkSocial;

    private Integer numberRequests;

    private Double payedValue;

    private Double valueToPay;

    private Double ranking;

    private Integer numberOfDeliveries;

    private Double numberOfKm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNib() {
        return nib;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkSocial() {
        return linkSocial;
    }

    public void setLinkSocial(String linkSocial) {
        this.linkSocial = linkSocial;
    }

    public Integer getNumberRequests() {
        return numberRequests;
    }

    public void setNumberRequests(Integer numberRequests) {
        this.numberRequests = numberRequests;
    }

    public Double getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(Double payedValue) {
        this.payedValue = payedValue;
    }

    public Double getValueToPay() {
        return valueToPay;
    }

    public void setValueToPay(Double valueToPay) {
        this.valueToPay = valueToPay;
    }

    public Double getRanking() {
        return ranking;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public Integer getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getNumberOfKm() {
        return numberOfKm;
    }

    public void setNumberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfoDTO)) {
            return false;
        }

        UserInfoDTO userInfoDTO = (UserInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoDTO{" +
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
