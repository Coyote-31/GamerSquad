package com.coyote.gamersquad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class EventSubTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventSub.class);
        EventSub eventSub1 = new EventSub();
        eventSub1.setId(1L);
        EventSub eventSub2 = new EventSub();
        eventSub2.setId(eventSub1.getId());
        assertThat(eventSub1).isEqualTo(eventSub2);
        eventSub2.setId(2L);
        assertThat(eventSub1).isNotEqualTo(eventSub2);
        eventSub1.setId(null);
        assertThat(eventSub1).isNotEqualTo(eventSub2);
    }
}
