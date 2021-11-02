package com.leb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.leb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RidePathDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RidePathDTO.class);
        RidePathDTO ridePathDTO1 = new RidePathDTO();
        ridePathDTO1.setId(1L);
        RidePathDTO ridePathDTO2 = new RidePathDTO();
        assertThat(ridePathDTO1).isNotEqualTo(ridePathDTO2);
        ridePathDTO2.setId(ridePathDTO1.getId());
        assertThat(ridePathDTO1).isEqualTo(ridePathDTO2);
        ridePathDTO2.setId(2L);
        assertThat(ridePathDTO1).isNotEqualTo(ridePathDTO2);
        ridePathDTO1.setId(null);
        assertThat(ridePathDTO1).isNotEqualTo(ridePathDTO2);
    }
}
