package com.leb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.leb.app.domain.Zone} entity. This class is used
 * in {@link com.leb.app.web.rest.ZoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /zones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter pointId;

    private LongFilter transportersId;

    public ZoneCriteria() {}

    public ZoneCriteria(ZoneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.pointId = other.pointId == null ? null : other.pointId.copy();
        this.transportersId = other.transportersId == null ? null : other.transportersId.copy();
    }

    @Override
    public ZoneCriteria copy() {
        return new ZoneCriteria(this);
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

    public LongFilter getTransportersId() {
        return transportersId;
    }

    public LongFilter transportersId() {
        if (transportersId == null) {
            transportersId = new LongFilter();
        }
        return transportersId;
    }

    public void setTransportersId(LongFilter transportersId) {
        this.transportersId = transportersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ZoneCriteria that = (ZoneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(pointId, that.pointId) &&
            Objects.equals(transportersId, that.transportersId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pointId, transportersId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZoneCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (pointId != null ? "pointId=" + pointId + ", " : "") +
            (transportersId != null ? "transportersId=" + transportersId + ", " : "") +
            "}";
    }
}
