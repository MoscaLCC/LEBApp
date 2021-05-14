package com.leb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Dimensions.
 */
@Entity
@Table(name = "dimensions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dimensions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "height")
    private Double height;

    @Column(name = "width")
    private Double width;

    @Column(name = "depth")
    private Double depth;

    @JsonIgnoreProperties(value = { "dimensions", "route", "producer" }, allowSetters = true)
    @OneToOne(mappedBy = "dimensions")
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dimensions id(Long id) {
        this.id = id;
        return this;
    }

    public Double getHeight() {
        return this.height;
    }

    public Dimensions height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return this.width;
    }

    public Dimensions width(Double width) {
        this.width = width;
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return this.depth;
    }

    public Dimensions depth(Double depth) {
        this.depth = depth;
        return this;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Request getRequest() {
        return this.request;
    }

    public Dimensions request(Request request) {
        this.setRequest(request);
        return this;
    }

    public void setRequest(Request request) {
        if (this.request != null) {
            this.request.setDimensions(null);
        }
        if (request != null) {
            request.setDimensions(this);
        }
        this.request = request;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dimensions)) {
            return false;
        }
        return id != null && id.equals(((Dimensions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dimensions{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", depth=" + getDepth() +
            "}";
    }
}
