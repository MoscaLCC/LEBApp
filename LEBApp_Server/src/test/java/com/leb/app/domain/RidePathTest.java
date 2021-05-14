package com.leb.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.leb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RidePathTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RidePath.class);
        RidePath ridePath1 = new RidePath();
        ridePath1.setId(1L);
        RidePath ridePath2 = new RidePath();
        ridePath2.setId(ridePath1.getId());
        assertThat(ridePath1).isEqualTo(ridePath2);
        ridePath2.setId(2L);
        assertThat(ridePath1).isNotEqualTo(ridePath2);
        ridePath1.setId(null);
        assertThat(ridePath1).isNotEqualTo(ridePath2);
    }
}
