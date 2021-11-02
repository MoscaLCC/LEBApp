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

    private StringFilter openingTime;

    private StringFilter closingTime;

    private StringFilter address;

    private IntegerFilter numberOfDeliveries;

    private LongFilter ownerPointId;

    private Boolean distinct;

    public PointCriteria() {}

    public PointCriteria(PointCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.openingTime = other.openingTime == null ? null : other.openingTime.copy();
        this.closingTime = other.closingTime == null ? null : other.closingTime.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.numberOfDeliveries = other.numberOfDeliveries == null ? null : other.numberOfDeliveries.copy();
        this.ownerPointId = other.ownerPointId == null ? null : other.ownerPointId.copy();
        this.distinct = other.distinct;
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

    public StringFilter getClosingTime() {
        return closingTime;
    }

    public StringFilter closingTime() {
        if (closingTime == null) {
            closingTime = new StringFilter();
        }
        return closingTime;
    }

    public void setClosingTime(StringFilter closingTime) {
        this.closingTime = closingTime;
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

    public LongFilter getOwnerPointId() {
        return ownerPointId;
    }

    public LongFilter ownerPointId() {
        if (ownerPointId == null) {
            ownerPointId = new LongFilter();
        }
        return ownerPointId;
    }

    public void setOwnerPointId(LongFilter ownerPointId) {
        this.ownerPointId = ownerPointId;
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
        final PointCriteria that = (PointCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(openingTime, that.openingTime) &&
            Objects.equals(closingTime, that.closingTime) &&
            Objects.equals(address, that.address) &&
            Objects.equals(numberOfDeliveries, that.numberOfDeliveries) &&
            Objects.equals(ownerPointId, that.ownerPointId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openingTime, closingTime, address, numberOfDeliveries, ownerPointId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (openingTime != null ? "openingTime=" + openingTime + ", " : "") +
            (closingTime != null ? "closingTime=" + closingTime + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (numberOfDeliveries != null ? "numberOfDeliveries=" + numberOfDeliveries + ", " : "") +
            (ownerPointId != null ? "ownerPointId=" + ownerPointId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
