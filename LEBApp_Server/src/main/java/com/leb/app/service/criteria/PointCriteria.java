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
 * Criteria class for the {@link com.leb.app.domain.Point} entity. This class is used
 * in {@link com.leb.app.web.rest.PointResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /points?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PointCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter openingTime;

    private IntegerFilter numberOfDeliveries;

    private DoubleFilter receivedValue;

    private DoubleFilter valueToReceive;

    private DoubleFilter ranking;

    private LongFilter userInfoId;

    private LongFilter deliveryManId;

    private LongFilter zoneId;

    public PointCriteria() {}

    public PointCriteria(PointCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.openingTime = other.openingTime == null ? null : other.openingTime.copy();
        this.numberOfDeliveries = other.numberOfDeliveries == null ? null : other.numberOfDeliveries.copy();
        this.receivedValue = other.receivedValue == null ? null : other.receivedValue.copy();
        this.valueToReceive = other.valueToReceive == null ? null : other.valueToReceive.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
        this.userInfoId = other.userInfoId == null ? null : other.userInfoId.copy();
        this.deliveryManId = other.deliveryManId == null ? null : other.deliveryManId.copy();
        this.zoneId = other.zoneId == null ? null : other.zoneId.copy();
    }

    @Override
    public PointCriteria copy() {
        return new PointCriteria(this);
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

    public StringFilter getOpeningTime() {
        return openingTime;
    }

    public StringFilter openingTime() {
        if (openingTime == null) {
            openingTime = new StringFilter();
        }
        return openingTime;
    }

    public void setOpeningTime(StringFilter openingTime) {
        this.openingTime = openingTime;
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

    public LongFilter getDeliveryManId() {
        return deliveryManId;
    }

    public LongFilter deliveryManId() {
        if (deliveryManId == null) {
            deliveryManId = new LongFilter();
        }
        return deliveryManId;
    }

    public void setDeliveryManId(LongFilter deliveryManId) {
        this.deliveryManId = deliveryManId;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public LongFilter zoneId() {
        if (zoneId == null) {
            zoneId = new LongFilter();
        }
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PointCriteria that = (PointCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(openingTime, that.openingTime) &&
            Objects.equals(numberOfDeliveries, that.numberOfDeliveries) &&
            Objects.equals(receivedValue, that.receivedValue) &&
            Objects.equals(valueToReceive, that.valueToReceive) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(userInfoId, that.userInfoId) &&
            Objects.equals(deliveryManId, that.deliveryManId) &&
            Objects.equals(zoneId, that.zoneId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openingTime, numberOfDeliveries, receivedValue, valueToReceive, ranking, userInfoId, deliveryManId, zoneId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (openingTime != null ? "openingTime=" + openingTime + ", " : "") +
            (numberOfDeliveries != null ? "numberOfDeliveries=" + numberOfDeliveries + ", " : "") +
            (receivedValue != null ? "receivedValue=" + receivedValue + ", " : "") +
            (valueToReceive != null ? "valueToReceive=" + valueToReceive + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (userInfoId != null ? "userInfoId=" + userInfoId + ", " : "") +
            (deliveryManId != null ? "deliveryManId=" + deliveryManId + ", " : "") +
            (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
            "}";
    }
}
