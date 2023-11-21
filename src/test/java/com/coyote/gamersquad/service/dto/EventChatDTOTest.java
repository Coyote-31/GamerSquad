package com.coyote.gamersquad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class EventChatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventChatDTO.class);
        EventChatDTO eventChatDTO1 = new EventChatDTO();
        eventChatDTO1.setId(1L);
        EventChatDTO eventChatDTO2 = new EventChatDTO();
        assertThat(eventChatDTO1).isNotEqualTo(eventChatDTO2);
        eventChatDTO2.setId(eventChatDTO1.getId());
        assertThat(eventChatDTO1).isEqualTo(eventChatDTO2);
        eventChatDTO2.setId(2L);
        assertThat(eventChatDTO1).isNotEqualTo(eventChatDTO2);
        eventChatDTO1.setId(null);
        assertThat(eventChatDTO1).isNotEqualTo(eventChatDTO2);
    }
}
