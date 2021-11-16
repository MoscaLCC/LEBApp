package com.leb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.leb.app.domain.UserInfo} entity. This class is used
 * in {@link com.leb.app.web.rest.UserInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phoneNumber;

    private StringFilter nib;

    private IntegerFilter nif;

    private InstantFilter birthday;

    private StringFilter address;

    private StringFilter linkSocial;

    private IntegerFilter numberRequests;

    private DoubleFilter payedValue;

    private DoubleFilter valueToPay;

    private DoubleFilter ranking;

    private IntegerFilter numberOfDeliveries;

    private DoubleFilter numberOfKm;

    private LongFilter requestsId;

    private LongFilter transportationsId;

    private LongFilter pointId;

    private Boolean distinct;

    public UserInfoCriteria() {}

    public UserInfoCriteria(UserInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.nib = other.nib == null ? null : other.nib.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.linkSocial = other.linkSocial == null ? null : other.linkSocial.copy();
        this.numberRequests = other.numberRequests == null ? null : other.numberRequests.copy();
        this.payedValue = other.payedValue == null ? null : other.payedValue.copy();
        this.valueToPay = other.valueToPay == null ? null : other.valueToPay.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
        this.numberOfDeliveries = other.numberOfDeliveries == null ? null : other.numberOfDeliveries.copy();
        this.numberOfKm = other.numberOfKm == null ? null : other.numberOfKm.copy();
        this.requestsId = other.requestsId == null ? null : other.requestsId.copy();
        this.transportationsId = other.transportationsId == null ? null : other.transportationsId.copy();
        this.pointId = other.pointId == null ? null : other.pointId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserInfoCriteria copy() {
        return new UserInfoCriteria(this);
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

    public InstantFilter getBirthday() {
        return birthday;
    }

    public InstantFilter birthday() {
        if (birthday == null) {
            birthday = new InstantFilter();
        }
        return birthday;
    }

    public void setBirthday(InstantFilter birthday) {
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

    public StringFilter getLinkSocial() {
        return linkSocial;
    }

    public StringFilter linkSocial() {
        if (linkSocial == null) {
            linkSocial = new StringFilter();
        }
        return linkSocial;
    }

    public void setLinkSocial(StringFilter linkSocial) {
        this.linkSocial = linkSocial;
    }

    public IntegerFilter getNumberRequests() {
        return numberRequests;
    }

    public IntegerFilter numberRequests() {
        if (numberRequests == null) {
            numberRequests = new IntegerFilter();
        }
        return numberRequests;
    }

    public void setNumberRequests(IntegerFilter numberRequests) {
        this.numberRequests = numberRequests;
    }

    public DoubleFilter getPayedValue() {
        return payedValue;
    }

    public DoubleFilter payedValue() {
        if (payedValue == null) {
            payedValue = new DoubleFilter();
        }
        return payedValue;
    }

    public void setPayedValue(DoubleFilter payedValue) {
        this.payedValue = payedValue;
    }

    public DoubleFilter getValueToPay() {
        return valueToPay;
    }

    public DoubleFilter valueToPay() {
        if (valueToPay == null) {
            valueToPay = new DoubleFilter();
        }
        return valueToPay;
    }

    public void setValueToPay(DoubleFilter valueToPay) {
        this.valueToPay = valueToPay;
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

    public LongFilter getRequestsId() {
        return requestsId;
    }

    public LongFilter requestsId() {
        if (requestsId == null) {
            requestsId = new LongFilter();
        }
        return requestsId;
    }

    public void setRequestsId(LongFilter requestsId) {
        this.requestsId = requestsId;
    }

    public LongFilter getTransportationsId() {
        return transportationsId;
    }

    public LongFilter transportationsId() {
        if (transportationsId == null) {
            transportationsId = new LongFilter();
        }
        return transportationsId;
    }

    public void setTransportationsId(LongFilter transportationsId) {
        this.transportationsId = transportationsId;
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

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserInfoCriteria that = (UserInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(nib, that.nib) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(address, that.address) &&
            Objects.equals(linkSocial, that.linkSocial) &&
            Objects.equals(numberRequests, that.numberRequests) &&
            Objects.equals(payedValue, that.payedValue) &&
            Objects.equals(valueToPay, that.valueToPay) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(numberOfDeliveries, that.numberOfDeliveries) &&
            Objects.equals(numberOfKm, that.numberOfKm) &&
            Objects.equals(requestsId, that.requestsId) &&
            Objects.equals(transportationsId, that.transportationsId) &&
            Objects.equals(pointId, that.pointId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            phoneNumber,
            nib,
            nif,
            birthday,
            address,
            linkSocial,
            numberRequests,
            payedValue,
            valueToPay,
            ranking,
            numberOfDeliveries,
            numberOfKm,
            requestsId,
            transportationsId,
            pointId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (nib != null ? "nib=" + nib + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (birthday != null ? "birthday=" + birthday + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (linkSocial != null ? "linkSocial=" + linkSocial + ", " : "") +
            (numberRequests != null ? "numberRequests=" + numberRequests + ", " : "") +
            (payedValue != null ? "payedValue=" + payedValue + ", " : "") +
            (valueToPay != null ? "valueToPay=" + valueToPay + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (numberOfDeliveries != null ? "numberOfDeliveries=" + numberOfDeliveries + ", " : "") +
            (numberOfKm != null ? "numberOfKm=" + numberOfKm + ", " : "") +
            (requestsId != null ? "requestsId=" + requestsId + ", " : "") +
            (transportationsId != null ? "transportationsId=" + transportationsId + ", " : "") +
            (pointId != null ? "pointId=" + pointId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
