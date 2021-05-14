package com.leb.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointMapperTest {

    private PointMapper pointMapper;

    @BeforeEach
    public void setUp() {
        pointMapper = new PointMapperImpl();
    }
}
