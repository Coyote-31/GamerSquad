package com.coyote.gamersquad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class GameSubDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameSubDTO.class);
        GameSubDTO gameSubDTO1 = new GameSubDTO();
        gameSubDTO1.setId(1L);
        GameSubDTO gameSubDTO2 = new GameSubDTO();
        assertThat(gameSubDTO1).isNotEqualTo(gameSubDTO2);
        gameSubDTO2.setId(gameSubDTO1.getId());
        assertThat(gameSubDTO1).isEqualTo(gameSubDTO2);
        gameSubDTO2.setId(2L);
        assertThat(gameSubDTO1).isNotEqualTo(gameSubDTO2);
        gameSubDTO1.setId(null);
        assertThat(gameSubDTO1).isNotEqualTo(gameSubDTO2);
    }
}
