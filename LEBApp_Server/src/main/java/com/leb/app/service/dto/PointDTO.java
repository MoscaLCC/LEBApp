package com.leb.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.Point} entity.
 */
public class PointDTO implements Serializable {

    private Long id;

    private String openingTime;

    private String closingTime;

    private Integer numberOfDeliveries;

    private Double receivedValue;

    private Double valueToReceive;

    private Double ranking;

    private UserInfoDTO userInfo;

    private ZoneDTO zone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public Integer getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getReceivedValue() {
        return receivedValue;
    }

    public void setReceivedValue(Double receivedValue) {
        this.receivedValue = receivedValue;
    }

    public Double getValueToReceive() {
        return valueToReceive;
    }

    public void setValueToReceive(Double valueToReceive) {
        this.valueToReceive = valueToReceive;
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

    public ZoneDTO getZone() {
        return zone;
    }

    public void setZone(ZoneDTO zone) {
        this.zone = zone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointDTO)) {
            return false;
        }

        PointDTO pointDTO = (PointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointDTO{" +
            "id=" + getId() +
            ", openingTime='" + getOpeningTime() + "'" +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", receivedValue=" + getReceivedValue() +
            ", valueToReceive=" + getValueToReceive() +
            ", ranking=" + getRanking() +
            ", userInfo=" + getUserInfo() +
            ", zone=" + getZone() +
            "}";
    }
}
