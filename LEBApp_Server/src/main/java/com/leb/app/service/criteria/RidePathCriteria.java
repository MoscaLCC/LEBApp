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
 * Criteria class for the {@link com.leb.app.domain.RidePath} entity. This class is used
 * in {@link com.leb.app.web.rest.RidePathResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ride-paths?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RidePathCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter source;

    private StringFilter destination;

    private StringFilter distance;

    private StringFilter estimatedTime;

    private LongFilter requestId;

    private LongFilter transportsId;

    public RidePathCriteria() {}

    public RidePathCriteria(RidePathCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.destination = other.destination == null ? null : other.destination.copy();
        this.distance = other.distance == null ? null : other.distance.copy();
        this.estimatedTime = other.estimatedTime == null ? null : other.estimatedTime.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
        this.transportsId = other.transportsId == null ? null : other.transportsId.copy();
    }

    @Override
    public RidePathCriteria copy() {
        return new RidePathCriteria(this);
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

    public StringFilter getSource() {
        return source;
    }

    public StringFilter source() {
        if (source == null) {
            source = new StringFilter();
        }
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public StringFilter getDestination() {
        return destination;
    }

    public StringFilter destination() {
        if (destination == null) {
            destination = new StringFilter();
        }
        return destination;
    }

    public void setDestination(StringFilter destination) {
        this.destination = destination;
    }

    public StringFilter getDistance() {
        return distance;
    }

    public StringFilter distance() {
        if (distance == null) {
            distance = new StringFilter();
        }
        return distance;
    }

    public void setDistance(StringFilter distance) {
        this.distance = distance;
    }

    public StringFilter getEstimatedTime() {
        return estimatedTime;
    }

    public StringFilter estimatedTime() {
        if (estimatedTime == null) {
            estimatedTime = new StringFilter();
        }
        return estimatedTime;
    }

    public void setEstimatedTime(StringFilter estimatedTime) {
        this.estimatedTime = estimatedTime;
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

    public LongFilter getTransportsId() {
        return transportsId;
    }

    public LongFilter transportsId() {
        if (transportsId == null) {
            transportsId = new LongFilter();
        }
        return transportsId;
    }

    public void setTransportsId(LongFilter transportsId) {
        this.transportsId = transportsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RidePathCriteria that = (RidePathCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(source, that.source) &&
            Objects.equals(destination, that.destination) &&
            Objects.equals(distance, that.distance) &&
            Objects.equals(estimatedTime, that.estimatedTime) &&
            Objects.equals(requestId, that.requestId) &&
            Objects.equals(transportsId, that.transportsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, destination, distance, estimatedTime, requestId, transportsId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RidePathCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (source != null ? "source=" + source + ", " : "") +
            (destination != null ? "destination=" + destination + ", " : "") +
            (distance != null ? "distance=" + distance + ", " : "") +
            (estimatedTime != null ? "estimatedTime=" + estimatedTime + ", " : "") +
            (requestId != null ? "requestId=" + requestId + ", " : "") +
            (transportsId != null ? "transportsId=" + transportsId + ", " : "") +
            "}";
    }
}