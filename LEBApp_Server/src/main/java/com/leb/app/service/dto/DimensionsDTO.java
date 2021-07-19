package com.leb.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.Dimensions} entity.
 */
public class DimensionsDTO implements Serializable {

    private Long id;

    private Double height;

    private Double width;

    private Double depth;

    private Double weight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DimensionsDTO)) {
            return false;
        }

        DimensionsDTO dimensionsDTO = (DimensionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dimensionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DimensionsDTO{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", depth=" + getDepth() +
            "}";
    }
}
