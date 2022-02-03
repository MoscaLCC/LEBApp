package com.leb.app.service.criteria;

import com.leb.app.domain.enumeration.Status;

import tech.jhipster.service.filter.Filter;

public class StatusFilter extends Filter<Status> {

    public StatusFilter() {}

    public StatusFilter(StatusFilter filter) {
        super(filter);
    }

    @Override
    public StatusFilter copy() {
        return new StatusFilter(this);
    }
}