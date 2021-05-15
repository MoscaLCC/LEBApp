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
 * Criteria class for the {@link com.leb.app.domain.Producer} entity. This class is used
 * in {@link com.leb.app.web.rest.ProducerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /producers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProducerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter linkSocial;

    private IntegerFilter numberRequests;

    private DoubleFilter payedValue;

    private DoubleFilter valueToPay;

    private DoubleFilter ranking;

    private LongFilter userInfoId;

    private LongFilter requestId;

    public ProducerCriteria() {}

    public ProducerCriteria(ProducerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.linkSocial = other.linkSocial == null ? null : other.linkSocial.copy();
        this.numberRequests = other.numberRequests == null ? null : other.numberRequests.copy();
        this.payedValue = other.payedValue == null ? null : other.payedValue.copy();
        this.valueToPay = other.valueToPay == null ? null : other.valueToPay.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
        this.userInfoId = other.userInfoId == null ? null : other.userInfoId.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
    }

    @Override
    public ProducerCriteria copy() {
        return new ProducerCriteria(this);
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

    public LongFilter getRequestId() {
        return requestId;
    }

    public LongFilter requestId() {
        if (requestId == null) {
            requestId = new LongFilter();
        }
        return requestId;
    }

    public void setRequestId(LongFilter requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProducerCriteria that = (ProducerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(linkSocial, that.linkSocial) &&
            Objects.equals(numberRequests, that.numberRequests) &&
            Objects.equals(payedValue, that.payedValue) &&
            Objects.equals(valueToPay, that.valueToPay) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(userInfoId, that.userInfoId) &&
            Objects.equals(requestId, that.requestId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, linkSocial, numberRequests, payedValue, valueToPay, ranking, userInfoId, requestId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProducerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (linkSocial != null ? "linkSocial=" + linkSocial + ", " : "") +
            (numberRequests != null ? "numberRequests=" + numberRequests + ", " : "") +
            (payedValue != null ? "payedValue=" + payedValue + ", " : "") +
            (valueToPay != null ? "valueToPay=" + valueToPay + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (userInfoId != null ? "userInfoId=" + userInfoId + ", " : "") +
            (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }
}
