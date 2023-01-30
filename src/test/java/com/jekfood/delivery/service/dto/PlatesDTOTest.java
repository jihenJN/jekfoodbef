package com.jekfood.delivery.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jekfood.delivery.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlatesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlatesDTO.class);
        PlatesDTO platesDTO1 = new PlatesDTO();
        platesDTO1.setId("id1");
        PlatesDTO platesDTO2 = new PlatesDTO();
        assertThat(platesDTO1).isNotEqualTo(platesDTO2);
        platesDTO2.setId(platesDTO1.getId());
        assertThat(platesDTO1).isEqualTo(platesDTO2);
        platesDTO2.setId("id2");
        assertThat(platesDTO1).isNotEqualTo(platesDTO2);
        platesDTO1.setId(null);
        assertThat(platesDTO1).isNotEqualTo(platesDTO2);
    }
}
