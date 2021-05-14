package com.leb.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransporterMapperTest {

    private TransporterMapper transporterMapper;

    @BeforeEach
    public void setUp() {
        transporterMapper = new TransporterMapperImpl();
    }
}
