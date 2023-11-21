package com.coyote.gamersquad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class EventChatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventChat.class);
        EventChat eventChat1 = new EventChat();
        eventChat1.setId(1L);
        EventChat eventChat2 = new EventChat();
        eventChat2.setId(eventChat1.getId());
        assertThat(eventChat1).isEqualTo(eventChat2);
        eventChat2.setId(2L);
        assertThat(eventChat1).isNotEqualTo(eventChat2);
        eventChat1.setId(null);
        assertThat(eventChat1).isNotEqualTo(eventChat2);
    }
}
