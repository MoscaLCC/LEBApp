package com.leb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.leb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointDTO.class);
        PointDTO pointDTO1 = new PointDTO();
        pointDTO1.setId(1L);
        PointDTO pointDTO2 = new PointDTO();
        assertThat(pointDTO1).isNotEqualTo(pointDTO2);
        pointDTO2.setId(pointDTO1.getId());
        assertThat(pointDTO1).isEqualTo(pointDTO2);
        pointDTO2.setId(2L);
        assertThat(pointDTO1).isNotEqualTo(pointDTO2);
        pointDTO1.setId(null);
        assertThat(pointDTO1).isNotEqualTo(pointDTO2);
    }
}
