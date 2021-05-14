package com.leb.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DimensionsMapperTest {

    private DimensionsMapper dimensionsMapper;

    @BeforeEach
    public void setUp() {
        dimensionsMapper = new DimensionsMapperImpl();
    }
}
