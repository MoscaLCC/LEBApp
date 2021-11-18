package com.leb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

public class RequestCriteria implements Serializable, Criteria {

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

    private DoubleFilter weight;

    private DoubleFilter hight;

    private DoubleFilter width;

    private StatusFilter status;

    private StringFilter estimatedDate;

    private StringFilter deliveryTime;

    private DoubleFilter shippingCosts;

    private DoubleFilter rating;

    private LongFilter ownerRequestId;

    private LongFilter tranporterId;

    private Boolean distinct;

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
        this.weight = other.weight == null ? null : other.weight.copy();
        this.hight = other.hight == null ? null : other.hight.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.estimatedDate = other.estimatedDate == null ? null : other.estimatedDate.copy();
        this.deliveryTime = other.deliveryTime == null ? null : other.deliveryTime.copy();
        this.shippingCosts = other.shippingCosts == null ? null : other.shippingCosts.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.ownerRequestId = other.ownerRequestId == null ? null : other.ownerRequestId.copy();
        this.tranporterId = other.tranporterId == null ? null : other.tranporterId.copy();
        this.distinct = other.distinct;
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

    public DoubleFilter getWeight() {
        return weight;
    }

    public DoubleFilter weight() {
        if (weight == null) {
            weight = new DoubleFilter();
        }
        return weight;
    }

    public void setWeight(DoubleFilter weight) {
        this.weight = weight;
    }

    public DoubleFilter getHight() {
        return hight;
    }

    public DoubleFilter hight() {
        if (hight == null) {
            hight = new DoubleFilter();
        }
        return hight;
    }

    public void setHight(DoubleFilter hight) {
        this.hight = hight;
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

    public LongFilter getOwnerRequestId() {
        return ownerRequestId;
    }

    public LongFilter ownerRequestId() {
        if (ownerRequestId == null) {
            ownerRequestId = new LongFilter();
        }
        return ownerRequestId;
    }

    public void setOwnerRequestId(LongFilter ownerRequestId) {
        this.ownerRequestId = ownerRequestId;
    }

    public LongFilter getTranporterId() {
        return tranporterId;
    }

    public LongFilter tranporterId() {
        if (tranporterId == null) {
            tranporterId = new LongFilter();
        }
        return tranporterId;
    }

    public void setTranporterId(LongFilter tranporterId) {
        this.tranporterId = tranporterId;
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
            Objects.equals(weight, that.weight) &&
            Objects.equals(hight, that.hight) &&
            Objects.equals(width, that.width) &&
            Objects.equals(status, that.status) &&
            Objects.equals(estimatedDate, that.estimatedDate) &&
            Objects.equals(deliveryTime, that.deliveryTime) &&
            Objects.equals(shippingCosts, that.shippingCosts) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(ownerRequestId, that.ownerRequestId) &&
            Objects.equals(tranporterId, that.tranporterId) &&
            Objects.equals(distinct, that.distinct)
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
            weight,
            hight,
            width,
            status,
            estimatedDate,
            deliveryTime,
            shippingCosts,
            rating,
            ownerRequestId,
            tranporterId,
            distinct
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
            (weight != null ? "weight=" + weight + ", " : "") +
            (hight != null ? "hight=" + hight + ", " : "") +
            (width != null ? "width=" + width + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (estimatedDate != null ? "estimatedDate=" + estimatedDate + ", " : "") +
            (deliveryTime != null ? "deliveryTime=" + deliveryTime + ", " : "") +
            (shippingCosts != null ? "shippingCosts=" + shippingCosts + ", " : "") +
            (rating != null ? "rating=" + rating + ", " : "") +
            (ownerRequestId != null ? "ownerRequestId=" + ownerRequestId + ", " : "") +
            (tranporterId != null ? "tranporterId=" + tranporterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
