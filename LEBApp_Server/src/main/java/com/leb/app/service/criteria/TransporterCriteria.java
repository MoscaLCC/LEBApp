package com.leb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.leb.app.domain.Transporter} entity. This class is used
 * in {@link com.leb.app.web.rest.TransporterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transporters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransporterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter favouriteTransport;

    private IntegerFilter numberOfDeliveries;

    private DoubleFilter numberOfKm;

    private DoubleFilter receivedValue;

    private DoubleFilter valueToReceive;

    private DoubleFilter ranking;

    private LongFilter userInfoId;

    private LongFilter ridePathId;

    private LongFilter zonesId;

    public TransporterCriteria() {}

    public TransporterCriteria(TransporterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.favouriteTransport = other.favouriteTransport == null ? null : other.favouriteTransport.copy();
        this.numberOfDeliveries = other.numberOfDeliveries == null ? null : other.numberOfDeliveries.copy();
        this.numberOfKm = other.numberOfKm == null ? null : other.numberOfKm.copy();
        this.receivedValue = other.receivedValue == null ? null : other.receivedValue.copy();
        this.valueToReceive = other.valueToReceive == null ? null : other.valueToReceive.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
        this.userInfoId = other.userInfoId == null ? null : other.userInfoId.copy();
        this.ridePathId = other.ridePathId == null ? null : other.ridePathId.copy();
        this.zonesId = other.zonesId == null ? null : other.zonesId.copy();
    }

    @Override
    public TransporterCriteria copy() {
        return new TransporterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFavouriteTransport() {
        return favouriteTransport;
    }

    public StringFilter favouriteTransport() {
        if (favouriteTransport == null) {
            favouriteTransport = new StringFilter();
        }
        return favouriteTransport;
    }

    public void setFavouriteTransport(StringFilter favouriteTransport) {
        this.favouriteTransport = favouriteTransport;
    }

    public IntegerFilter getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public IntegerFilter numberOfDeliveries() {
        if (numberOfDeliveries == null) {
            numberOfDeliveries = new IntegerFilter();
        }
        return numberOfDeliveries;
    }

    public void setNumberOfDeliveries(IntegerFilter numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public DoubleFilter getNumberOfKm() {
        return numberOfKm;
    }

    public DoubleFilter numberOfKm() {
        if (numberOfKm == null) {
            numberOfKm = new DoubleFilter();
        }
        return numberOfKm;
    }

    public void setNumberOfKm(DoubleFilter numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public DoubleFilter getReceivedValue() {
        return receivedValue;
    }

    public DoubleFilter receivedValue() {
        if (receivedValue == null) {
            receivedValue = new DoubleFilter();
        }
        return receivedValue;
    }

    public void setReceivedValue(DoubleFilter receivedValue) {
        this.receivedValue = receivedValue;
    }

    public DoubleFilter getValueToReceive() {
        return valueToReceive;
    }

    public DoubleFilter valueToReceive() {
        if (valueToReceive == null) {
            valueToReceive = new DoubleFilter();
        }
        return valueToReceive;
    }

    public void setValueToReceive(DoubleFilter valueToReceive) {
        this.valueToReceive = valueToReceive;
    }

    public DoubleFilter getRanking() {
        return ranking;
    }

    public DoubleFilter ranking() {
        if (ranking == null) {
            ranking = new DoubleFilter();
        }
        return ranking;
    }

    public void setRanking(DoubleFilter ranking) {
        this.ranking = ranking;
    }

    public LongFilter getUserInfoId() {
        return userInfoId;
    }

    public LongFilter userInfoId() {
        if (userInfoId == null) {
            userInfoId = new LongFilter();
        }
        return userInfoId;
    }

    public void setUserInfoId(LongFilter userInfoId) {
        this.userInfoId = userInfoId;
    }

    public LongFilter getRidePathId() {
        return ridePathId;
    }

    public LongFilter ridePathId() {
        if (ridePathId == null) {
            ridePathId = new LongFilter();
        }
        return ridePathId;
    }

    public void setRidePathId(LongFilter ridePathId) {
        this.ridePathId = ridePathId;
    }

    public LongFilter getZonesId() {
        return zonesId;
    }

    public LongFilter zonesId() {
        if (zonesId == null) {
            zonesId = new LongFilter();
        }
        return zonesId;
    }

    public void setZonesId(LongFilter zonesId) {
        this.zonesId = zonesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransporterCriteria that = (TransporterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(favouriteTransport, that.favouriteTransport) &&
            Objects.equals(numberOfDeliveries, that.numberOfDeliveries) &&
            Objects.equals(numberOfKm, that.numberOfKm) &&
            Objects.equals(receivedValue, that.receivedValue) &&
            Objects.equals(valueToReceive, that.valueToReceive) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(userInfoId, that.userInfoId) &&
            Objects.equals(ridePathId, that.ridePathId) &&
            Objects.equals(zonesId, that.zonesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            favouriteTransport,
            numberOfDeliveries,
            numberOfKm,
            receivedValue,
            valueToReceive,
            ranking,
            userInfoId,
            ridePathId,
            zonesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransporterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (favouriteTransport != null ? "favouriteTransport=" + favouriteTransport + ", " : "") +
            (numberOfDeliveries != null ? "numberOfDeliveries=" + numberOfDeliveries + ", " : "") +
            (numberOfKm != null ? "numberOfKm=" + numberOfKm + ", " : "") +
            (receivedValue != null ? "receivedValue=" + receivedValue + ", " : "") +
            (valueToReceive != null ? "valueToReceive=" + valueToReceive + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (userInfoId != null ? "userInfoId=" + userInfoId + ", " : "") +
            (ridePathId != null ? "ridePathId=" + ridePathId + ", " : "") +
            (zonesId != null ? "zonesId=" + zonesId + ", " : "") +
            "}";
    }
}
