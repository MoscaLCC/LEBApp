package com.leb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
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

    private StringFilter name;

    private StringFilter email;

    private StringFilter phoneNumber;

    private StringFilter nib;

    private IntegerFilter nif;

    private StringFilter address;

    private StringFilter openingTime;

    private IntegerFilter numberOfDeliveries;

    private DoubleFilter receivedValue;

    private DoubleFilter valueToReceive;

    private DoubleFilter ranking;

    private LongFilter deliveryManId;

    private LongFilter zoneId;

    public PointCriteria() {}

    public PointCriteria(PointCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.nib = other.nib == null ? null : other.nib.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.openingTime = other.openingTime == null ? null : other.openingTime.copy();
        this.numberOfDeliveries = other.numberOfDeliveries == null ? null : other.numberOfDeliveries.copy();
        this.receivedValue = other.receivedValue == null ? null : other.receivedValue.copy();
        this.valueToReceive = other.valueToReceive == null ? null : other.valueToReceive.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getNib() {
        return nib;
    }

    public StringFilter nib() {
        if (nib == null) {
            nib = new StringFilter();
        }
        return nib;
    }

    public void setNib(StringFilter nib) {
        this.nib = nib;
    }

    public IntegerFilter getNif() {
        return nif;
    }

    public IntegerFilter nif() {
        if (nif == null) {
            nif = new IntegerFilter();
        }
        return nif;
    }

    public void setNif(IntegerFilter nif) {
        this.nif = nif;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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
            Objects.equals(name, that.name) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(nib, that.nib) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(address, that.address) &&
            Objects.equals(openingTime, that.openingTime) &&
            Objects.equals(numberOfDeliveries, that.numberOfDeliveries) &&
            Objects.equals(receivedValue, that.receivedValue) &&
            Objects.equals(valueToReceive, that.valueToReceive) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(deliveryManId, that.deliveryManId) &&
            Objects.equals(zoneId, that.zoneId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            email,
            phoneNumber,
            nib,
            nif,
            address,
            openingTime,
            numberOfDeliveries,
            receivedValue,
            valueToReceive,
            ranking,
            deliveryManId,
            zoneId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (nib != null ? "nib=" + nib + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (openingTime != null ? "openingTime=" + openingTime + ", " : "") +
            (numberOfDeliveries != null ? "numberOfDeliveries=" + numberOfDeliveries + ", " : "") +
            (receivedValue != null ? "receivedValue=" + receivedValue + ", " : "") +
            (valueToReceive != null ? "valueToReceive=" + valueToReceive + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (deliveryManId != null ? "deliveryManId=" + deliveryManId + ", " : "") +
            (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
            "}";
    }
}
