package com.jekfood.delivery.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jekfood.delivery.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlatesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plates.class);
        Plates plates1 = new Plates();
        plates1.setId("id1");
        Plates plates2 = new Plates();
        plates2.setId(plates1.getId());
        assertThat(plates1).isEqualTo(plates2);
        plates2.setId("id2");
        assertThat(plates1).isNotEqualTo(plates2);
        plates1.setId(null);
        assertThat(plates1).isNotEqualTo(plates2);
    }
}
