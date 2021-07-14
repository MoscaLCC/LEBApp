package com.leb.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.Producer} entity.
 */
public class ProducerDTO implements Serializable {

    private Long id;

    private String linkSocial;

    private Integer numberRequests;

    private Double payedValue;

    private Double valueToPay;

    private Double ranking;

    private UserInfoDTO userInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProducerDTO)) {
            return false;
        }

        ProducerDTO producerDTO = (ProducerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, producerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProducerDTO{" +
            "id=" + getId() +
            ", linkSocial='" + getLinkSocial() + "'" +
            ", numberRequests=" + getNumberRequests() +
            ", payedValue=" + getPayedValue() +
            ", valueToPay=" + getValueToPay() +
            ", ranking=" + getRanking() +
            ", userInfo=" + getUserInfo() +
            "}";
    }
}
