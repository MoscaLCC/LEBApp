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
 * Criteria class for the {@link com.leb.app.domain.Dimensions} entity. This class is used
 * in {@link com.leb.app.web.rest.DimensionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dimensions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DimensionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter height;

    private DoubleFilter width;

    private DoubleFilter depth;

    private LongFilter requestId;

    public DimensionsCriteria() {}

    public DimensionsCriteria(DimensionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.depth = other.depth == null ? null : other.depth.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
    }

    @Override
    public DimensionsCriteria copy() {
        return new DimensionsCriteria(this);
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

    public DoubleFilter getHeight() {
        return height;
    }

    public DoubleFilter height() {
        if (height == null) {
            height = new DoubleFilter();
        }
        return height;
    }

    public void setHeight(DoubleFilter height) {
        this.height = height;
    }

    public DoubleFilter getWidth() {
        return width;
    }

    public DoubleFilter width() {
        if (width == null) {
            width = new DoubleFilter();
        }
        return width;
    }

    public void setWidth(DoubleFilter width) {
        this.width = width;
    }

    public DoubleFilter getDepth() {
        return depth;
    }

    public DoubleFilter depth() {
        if (depth == null) {
            depth = new DoubleFilter();
        }
        return depth;
    }

    public void setDepth(DoubleFilter depth) {
        this.depth = depth;
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
        final DimensionsCriteria that = (DimensionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(height, that.height) &&
            Objects.equals(width, that.width) &&
            Objects.equals(depth, that.depth) &&
            Objects.equals(requestId, that.requestId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, height, width, depth, requestId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DimensionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (height != null ? "height=" + height + ", " : "") +
            (width != null ? "width=" + width + ", " : "") +
            (depth != null ? "depth=" + depth + ", " : "") +
            (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }
}
