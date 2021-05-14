package com.leb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.leb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransporterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransporterDTO.class);
        TransporterDTO transporterDTO1 = new TransporterDTO();
        transporterDTO1.setId(1L);
        TransporterDTO transporterDTO2 = new TransporterDTO();
        assertThat(transporterDTO1).isNotEqualTo(transporterDTO2);
        transporterDTO2.setId(transporterDTO1.getId());
        assertThat(transporterDTO1).isEqualTo(transporterDTO2);
        transporterDTO2.setId(2L);
        assertThat(transporterDTO1).isNotEqualTo(transporterDTO2);
        transporterDTO1.setId(null);
        assertThat(transporterDTO1).isNotEqualTo(transporterDTO2);
    }
}
