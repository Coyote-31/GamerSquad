package com.coyote.gamersquad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class EventSubDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventSubDTO.class);
        EventSubDTO eventSubDTO1 = new EventSubDTO();
        eventSubDTO1.setId(1L);
        EventSubDTO eventSubDTO2 = new EventSubDTO();
        assertThat(eventSubDTO1).isNotEqualTo(eventSubDTO2);
        eventSubDTO2.setId(eventSubDTO1.getId());
        assertThat(eventSubDTO1).isEqualTo(eventSubDTO2);
        eventSubDTO2.setId(2L);
        assertThat(eventSubDTO1).isNotEqualTo(eventSubDTO2);
        eventSubDTO1.setId(null);
        assertThat(eventSubDTO1).isNotEqualTo(eventSubDTO2);
    }
}
