package com.coyote.gamersquad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class GameSubTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameSub.class);
        GameSub gameSub1 = new GameSub();
        gameSub1.setId(1L);
        GameSub gameSub2 = new GameSub();
        gameSub2.setId(gameSub1.getId());
        assertThat(gameSub1).isEqualTo(gameSub2);
        gameSub2.setId(2L);
        assertThat(gameSub1).isNotEqualTo(gameSub2);
        gameSub1.setId(null);
        assertThat(gameSub1).isNotEqualTo(gameSub2);
    }
}
