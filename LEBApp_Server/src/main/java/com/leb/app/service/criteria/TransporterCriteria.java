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

    private StringFilter name;

    private StringFilter email;

    private StringFilter phoneNumber;

    private StringFilter nib;

    private IntegerFilter nif;

    private LocalDateFilter birthday;

    private StringFilter address;

    private StringFilter photo;

    private StringFilter favouriteTransport;

    private IntegerFilter numberOfDeliveries;

    private DoubleFilter numberOfKm;

    private DoubleFilter receivedValue;

    private DoubleFilter valueToReceive;

    private DoubleFilter ranking;

    private LongFilter ridePathId;

    private LongFilter zonesId;

    public TransporterCriteria() {}

    public TransporterCriteria(TransporterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.nib = other.nib == null ? null : other.nib.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.photo = other.photo == null ? null : other.photo.copy();
        this.favouriteTransport = other.favouriteTransport == null ? null : other.favouriteTransport.copy();
        this.numberOfDeliveries = other.numberOfDeliveries == null ? null : other.numberOfDeliveries.copy();
        this.numberOfKm = other.numberOfKm == null ? null : other.numberOfKm.copy();
        this.receivedValue = other.receivedValue == null ? null : other.receivedValue.copy();
        this.valueToReceive = other.valueToReceive == null ? null : other.valueToReceive.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
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
            Objects.equals(name, that.name) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(nib, that.nib) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(address, that.address) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(favouriteTransport, that.favouriteTransport) &&
            Objects.equals(numberOfDeliveries, that.numberOfDeliveries) &&
            Objects.equals(numberOfKm, that.numberOfKm) &&
            Objects.equals(receivedValue, that.receivedValue) &&
            Objects.equals(valueToReceive, that.valueToReceive) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(ridePathId, that.ridePathId) &&
            Objects.equals(zonesId, that.zonesId)
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
            birthday,
            address,
            photo,
            favouriteTransport,
            numberOfDeliveries,
            numberOfKm,
            receivedValue,
            valueToReceive,
            ranking,
            ridePathId,
            zonesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransporterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (nib != null ? "nib=" + nib + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (birthday != null ? "birthday=" + birthday + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (photo != null ? "photo=" + photo + ", " : "") +
            (favouriteTransport != null ? "favouriteTransport=" + favouriteTransport + ", " : "") +
            (numberOfDeliveries != null ? "numberOfDeliveries=" + numberOfDeliveries + ", " : "") +
            (numberOfKm != null ? "numberOfKm=" + numberOfKm + ", " : "") +
            (receivedValue != null ? "receivedValue=" + receivedValue + ", " : "") +
            (valueToReceive != null ? "valueToReceive=" + valueToReceive + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (ridePathId != null ? "ridePathId=" + ridePathId + ", " : "") +
            (zonesId != null ? "zonesId=" + zonesId + ", " : "") +
            "}";
    }
}
