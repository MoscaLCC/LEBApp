package com.leb.app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.leb.app.domain.Transporter} entity.
 */
public class TransporterDTO implements Serializable {

    private Long id;

    private String favouriteTransport;

    private Integer numberOfDeliveries;

    private Double numberOfKm;

    private Double receivedValue;

    private Double valueToReceive;

    private Double ranking;

    private UserInfoDTO userInfo;

    private Set<RidePathDTO> ridePaths = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFavouriteTransport() {
        return favouriteTransport;
    }

    public void setFavouriteTransport(String favouriteTransport) {
        this.favouriteTransport = favouriteTransport;
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

    public Set<RidePathDTO> getRidePaths() {
        return ridePaths;
    }

    public void setRidePaths(Set<RidePathDTO> ridePaths) {
        this.ridePaths = ridePaths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransporterDTO)) {
            return false;
        }

        TransporterDTO transporterDTO = (TransporterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transporterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransporterDTO{" +
            "id=" + getId() +
            ", favouriteTransport='" + getFavouriteTransport() + "'" +
            ", numberOfDeliveries=" + getNumberOfDeliveries() +
            ", numberOfKm=" + getNumberOfKm() +
            ", receivedValue=" + getReceivedValue() +
            ", valueToReceive=" + getValueToReceive() +
            ", ranking=" + getRanking() +
            ", userInfo=" + getUserInfo() +
            ", ridePaths=" + getRidePaths() +
            "}";
    }
}
