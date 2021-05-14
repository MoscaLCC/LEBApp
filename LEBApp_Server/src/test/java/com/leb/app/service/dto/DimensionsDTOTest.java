package com.leb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.leb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DimensionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DimensionsDTO.class);
        DimensionsDTO dimensionsDTO1 = new DimensionsDTO();
        dimensionsDTO1.setId(1L);
        DimensionsDTO dimensionsDTO2 = new DimensionsDTO();
        assertThat(dimensionsDTO1).isNotEqualTo(dimensionsDTO2);
        dimensionsDTO2.setId(dimensionsDTO1.getId());
        assertThat(dimensionsDTO1).isEqualTo(dimensionsDTO2);
        dimensionsDTO2.setId(2L);
        assertThat(dimensionsDTO1).isNotEqualTo(dimensionsDTO2);
        dimensionsDTO1.setId(null);
        assertThat(dimensionsDTO1).isNotEqualTo(dimensionsDTO2);
    }
}
