package com.leb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.leb.app.domain.DeliveryMan} entity. This class is used
 * in {@link com.leb.app.web.rest.DeliveryManResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delivery-men?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeliveryManCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter email;

    private StringFilter phoneNumber;

    private IntegerFilter nif;

    private StringFilter nib;

    private LocalDateFilter birthday;

    private StringFilter address;

    private StringFilter photo;

    private StringFilter openingTime;

    private IntegerFilter numberOfDeliveries;

    private DoubleFilter numberOfKm;

    private DoubleFilter receivedValue;

    private DoubleFilter valueToReceive;

    private DoubleFilter ranking;

    private LongFilter pointId;

    public DeliveryManCriteria() {}

    public DeliveryManCriteria(DeliveryManCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.nib = other.nib == null ? null : other.nib.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.photo = other.photo == null ? null : other.photo.copy();
        this.openingTime = other.openingTime == null ? null : other.openingTime.copy();
        this.numberOfDeliveries = other.numberOfDeliveries == null ? null : other.numberOfDeliveries.copy();
        this.numberOfKm = other.numberOfKm == null ? null : other.numberOfKm.copy();
        this.receivedValue = other.receivedValue == null ? null : other.receivedValue.copy();
        this.valueToReceive = other.valueToReceive == null ? null : other.valueToReceive.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
        this.pointId = other.pointId == null ? null : other.pointId.copy();
    }

    @Override
    public DeliveryManCriteria copy() {
        return new DeliveryManCriteria(this);
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

    public LocalDateFilter getBirthday() {
        return birthday;
    }

    public LocalDateFilter birthday() {
        if (birthday == null) {
            birthday = new LocalDateFilter();
        }
        return birthday;
    }

    public void setBirthday(LocalDateFilter birthday) {
        this.birthday = birthday;
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

    public StringFilter getPhoto() {
        return photo;
    }

    public StringFilter photo() {
        if (photo == null) {
            photo = new StringFilter();
        }
        return photo;
    }

    public void setPhoto(StringFilter photo) {
        this.photo = photo;
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

    public LongFilter getPointId() {
        return pointId;
    }

    public LongFilter pointId() {
        if (pointId == null) {
            pointId = new LongFilter();
        }
        return pointId;
    }

    public void setPointId(LongFilter pointId) {
        this.pointId = pointId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeliveryManCriteria that = (DeliveryManCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(nib, that.nib) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(address, that.address) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(openingTime, that.openingTime) &&
            Objects.equals(numberOfDeliveries, that.numberOfDeliveries) &&
            Objects.equals(numberOfKm, that.numberOfKm) &&
            Objects.equals(receivedValue, that.receivedValue) &&
            Objects.equals(valueToReceive, that.valueToReceive) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(pointId, that.pointId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            email,
            phoneNumber,
            nif,
            nib,
            birthday,
            address,
            photo,
            openingTime,
            numberOfDeliveries,
            numberOfKm,
            receivedValue,
            valueToReceive,
            ranking,
            pointId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryManCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (nib != null ? "nib=" + nib + ", " : "") +
            (birthday != null ? "birthday=" + birthday + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (photo != null ? "photo=" + photo + ", " : "") +
            (openingTime != null ? "openingTime=" + openingTime + ", " : "") +
            (numberOfDeliveries != null ? "numberOfDeliveries=" + numberOfDeliveries + ", " : "") +
            (numberOfKm != null ? "numberOfKm=" + numberOfKm + ", " : "") +
            (receivedValue != null ? "receivedValue=" + receivedValue + ", " : "") +
            (valueToReceive != null ? "valueToReceive=" + valueToReceive + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (pointId != null ? "pointId=" + pointId + ", " : "") +
            "}";
    }
}
