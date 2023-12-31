package com.coyote.gamersquad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = new AppUser();
        appUser1.setId(1L);
        AppUser appUser2 = new AppUser();
        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);
        appUser2.setId(2L);
        assertThat(appUser1).isNotEqualTo(appUser2);
        appUser1.setId(null);
        assertThat(appUser1).isNotEqualTo(appUser2);
    }
}
