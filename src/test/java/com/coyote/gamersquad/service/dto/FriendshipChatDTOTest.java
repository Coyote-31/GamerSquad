package com.coyote.gamersquad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.coyote.gamersquad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class FriendshipChatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendshipChatDTO.class);
        FriendshipChatDTO friendshipChatDTO1 = new FriendshipChatDTO();
        friendshipChatDTO1.setId(1L);
        FriendshipChatDTO friendshipChatDTO2 = new FriendshipChatDTO();
        assertThat(friendshipChatDTO1).isNotEqualTo(friendshipChatDTO2);
        friendshipChatDTO2.setId(friendshipChatDTO1.getId());
        assertThat(friendshipChatDTO1).isEqualTo(friendshipChatDTO2);
        friendshipChatDTO2.setId(2L);
        assertThat(friendshipChatDTO1).isNotEqualTo(friendshipChatDTO2);
        friendshipChatDTO1.setId(null);
        assertThat(friendshipChatDTO1).isNotEqualTo(friendshipChatDTO2);
    }
}
