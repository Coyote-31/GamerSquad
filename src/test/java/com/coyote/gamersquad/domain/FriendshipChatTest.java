package com.coyote.gamersquad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class FriendshipChatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendshipChat.class);
        FriendshipChat friendshipChat1 = new FriendshipChat();
        friendshipChat1.setId(1L);
        FriendshipChat friendshipChat2 = new FriendshipChat();
        friendshipChat2.setId(friendshipChat1.getId());
        assertThat(friendshipChat1).isEqualTo(friendshipChat2);
        friendshipChat2.setId(2L);
        assertThat(friendshipChat1).isNotEqualTo(friendshipChat2);
        friendshipChat1.setId(null);
        assertThat(friendshipChat1).isNotEqualTo(friendshipChat2);
    }
}
