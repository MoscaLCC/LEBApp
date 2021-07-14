package com.leb.app.service.criteria;

import com.leb.app.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link com.leb.app.domain.Request} entity. This class is used
 * in {@link com.leb.app.web.rest.RequestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequestCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter productValue;

    private StringFilter productName;

    private StringFilter source;

    private StringFilter destination;

    private StringFilter destinationContact;

    private StringFilter initDate;

    private StringFilter expirationDate;

    private StringFilter description;

    private StringFilter specialCharacteristics;

    private DoubleFilter productWeight;

    private StatusFilter status;

    private StringFilter estimatedDate;

    private StringFilter deliveryTime;

    private DoubleFilter shippingCosts;

    private DoubleFilter rating;

    private LongFilter dimensionsId;

    private LongFilter ridePathId;

    private LongFilter producerId;

    public RequestCriteria() {}

    public RequestCriteria(RequestCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productValue = other.productValue == null ? null : other.productValue.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.destination = other.destination == null ? null : other.destination.copy();
        this.destinationContact = other.destinationContact == null ? null : other.destinationContact.copy();
        this.initDate = other.initDate == null ? null : other.initDate.copy();
        this.expirationDate = other.expirationDate == null ? null : other.expirationDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.specialCharacteristics = other.specialCharacteristics == null ? null : other.specialCharacteristics.copy();
        this.productWeight = other.productWeight == null ? null : other.productWeight.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.estimatedDate = other.estimatedDate == null ? null : other.estimatedDate.copy();
        this.deliveryTime = other.deliveryTime == null ? null : other.deliveryTime.copy();
        this.shippingCosts = other.shippingCosts == null ? null : other.shippingCosts.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.dimensionsId = other.dimensionsId == null ? null : other.dimensionsId.copy();
        this.ridePathId = other.ridePathId == null ? null : other.ridePathId.copy();
        this.producerId = other.producerId == null ? null : other.producerId.copy();
    }

    @Override
    public RequestCriteria copy() {
        return new RequestCriteria(this);
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

    public DoubleFilter getProductValue() {
        return productValue;
    }

    public DoubleFilter productValue() {
        if (productValue == null) {
            productValue = new DoubleFilter();
        }
        return productValue;
    }

    public void setProductValue(DoubleFilter productValue) {
        this.productValue = productValue;
    }

    public StringFilter getProductName() {
        return productName;
    }

    public StringFilter productName() {
        if (productName == null) {
            productName = new StringFilter();
        }
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
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

    public StringFilter getDestinationContact() {
        return destinationContact;
    }

    public StringFilter destinationContact() {
        if (destinationContact == null) {
            destinationContact = new StringFilter();
        }
        return destinationContact;
    }

    public void setDestinationContact(StringFilter destinationContact) {
        this.destinationContact = destinationContact;
    }

    public StringFilter getInitDate() {
        return initDate;
    }

    public StringFilter initDate() {
        if (initDate == null) {
            initDate = new StringFilter();
        }
        return initDate;
    }

    public void setInitDate(StringFilter initDate) {
        this.initDate = initDate;
    }

    public StringFilter getExpirationDate() {
        return expirationDate;
    }

    public StringFilter expirationDate() {
        if (expirationDate == null) {
            expirationDate = new StringFilter();
        }
        return expirationDate;
    }

    public void setExpirationDate(StringFilter expirationDate) {
        this.expirationDate = expirationDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getSpecialCharacteristics() {
        return specialCharacteristics;
    }

    public StringFilter specialCharacteristics() {
        if (specialCharacteristics == null) {
            specialCharacteristics = new StringFilter();
        }
        return specialCharacteristics;
    }

    public void setSpecialCharacteristics(StringFilter specialCharacteristics) {
        this.specialCharacteristics = specialCharacteristics;
    }

    public DoubleFilter getProductWeight() {
        return productWeight;
    }

    public DoubleFilter productWeight() {
        if (productWeight == null) {
            productWeight = new DoubleFilter();
        }
        return productWeight;
    }

    public void setProductWeight(DoubleFilter productWeight) {
        this.productWeight = productWeight;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getEstimatedDate() {
        return estimatedDate;
    }

    public StringFilter estimatedDate() {
        if (estimatedDate == null) {
            estimatedDate = new StringFilter();
        }
        return estimatedDate;
    }

    public void setEstimatedDate(StringFilter estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public StringFilter getDeliveryTime() {
        return deliveryTime;
    }

    public StringFilter deliveryTime() {
        if (deliveryTime == null) {
            deliveryTime = new StringFilter();
        }
        return deliveryTime;
    }

    public void setDeliveryTime(StringFilter deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public DoubleFilter getShippingCosts() {
        return shippingCosts;
    }

    public DoubleFilter shippingCosts() {
        if (shippingCosts == null) {
            shippingCosts = new DoubleFilter();
        }
        return shippingCosts;
    }

    public void setShippingCosts(DoubleFilter shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    public DoubleFilter getRating() {
        return rating;
    }

    public DoubleFilter rating() {
        if (rating == null) {
            rating = new DoubleFilter();
        }
        return rating;
    }

    public void setRating(DoubleFilter rating) {
        this.rating = rating;
    }

    public LongFilter getDimensionsId() {
        return dimensionsId;
    }

    public LongFilter dimensionsId() {
        if (dimensionsId == null) {
            dimensionsId = new LongFilter();
        }
        return dimensionsId;
    }

    public void setDimensionsId(LongFilter dimensionsId) {
        this.dimensionsId = dimensionsId;
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

    public LongFilter getProducerId() {
        return producerId;
    }

    public LongFilter producerId() {
        if (producerId == null) {
            producerId = new LongFilter();
        }
        return producerId;
    }

    public void setProducerId(LongFilter producerId) {
        this.producerId = producerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequestCriteria that = (RequestCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(productValue, that.productValue) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(source, that.source) &&
            Objects.equals(destination, that.destination) &&
            Objects.equals(destinationContact, that.destinationContact) &&
            Objects.equals(initDate, that.initDate) &&
            Objects.equals(expirationDate, that.expirationDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(specialCharacteristics, that.specialCharacteristics) &&
            Objects.equals(productWeight, that.productWeight) &&
            Objects.equals(status, that.status) &&
            Objects.equals(estimatedDate, that.estimatedDate) &&
            Objects.equals(deliveryTime, that.deliveryTime) &&
            Objects.equals(shippingCosts, that.shippingCosts) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(dimensionsId, that.dimensionsId) &&
            Objects.equals(ridePathId, that.ridePathId) &&
            Objects.equals(producerId, that.producerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            productValue,
            productName,
            source,
            destination,
            destinationContact,
            initDate,
            expirationDate,
            description,
            specialCharacteristics,
            productWeight,
            status,
            estimatedDate,
            deliveryTime,
            shippingCosts,
            rating,
            dimensionsId,
            ridePathId,
            producerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (productValue != null ? "productValue=" + productValue + ", " : "") +
            (productName != null ? "productName=" + productName + ", " : "") +
            (source != null ? "source=" + source + ", " : "") +
            (destination != null ? "destination=" + destination + ", " : "") +
            (destinationContact != null ? "destinationContact=" + destinationContact + ", " : "") +
            (initDate != null ? "initDate=" + initDate + ", " : "") +
            (expirationDate != null ? "expirationDate=" + expirationDate + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (specialCharacteristics != null ? "specialCharacteristics=" + specialCharacteristics + ", " : "") +
            (productWeight != null ? "productWeight=" + productWeight + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (estimatedDate != null ? "estimatedDate=" + estimatedDate + ", " : "") +
            (deliveryTime != null ? "deliveryTime=" + deliveryTime + ", " : "") +
            (shippingCosts != null ? "shippingCosts=" + shippingCosts + ", " : "") +
            (rating != null ? "rating=" + rating + ", " : "") +
            (dimensionsId != null ? "dimensionsId=" + dimensionsId + ", " : "") +
            (ridePathId != null ? "ridePathId=" + ridePathId + ", " : "") +
            (producerId != null ? "producerId=" + producerId + ", " : "") +
            "}";
    }
}
