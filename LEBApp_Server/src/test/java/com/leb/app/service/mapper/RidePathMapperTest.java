package com.leb.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RidePathMapperTest {

    private RidePathMapper ridePathMapper;

    @BeforeEach
    public void setUp() {
        ridePathMapper = new RidePathMapperImpl();
    }
}
