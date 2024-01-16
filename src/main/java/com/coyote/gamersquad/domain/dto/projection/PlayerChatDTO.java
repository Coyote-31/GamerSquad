package com.coyote.gamersquad.domain.dto.projection;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO to display a player chat message between two friends in the view.
 */
public class PlayerChatDTO implements Serializable {

    private Long senderUserId;

    private String senderUserLogin;

    private String senderUserImageUrl;

    private Long senderAppUserId;

    private Long friendshipId;

    private Long friendshipChatId;

    private String friendshipChatMessage;

    private Instant friendshipChatSendAt;

    public PlayerChatDTO(
        Long senderUserId,
        String senderUserLogin,
        String senderUserImageUrl,
        Long senderAppUserId,
        Long friendshipId,
        Long friendshipChatId,
        String friendshipChatMessage,
        Instant friendshipChatSendAt
    ) {
        this.senderUserId = senderUserId;
        this.senderUserLogin = senderUserLogin;
        this.senderUserImageUrl = senderUserImageUrl;
        this.senderAppUserId = senderAppUserId;
        this.friendshipId = friendshipId;
        this.friendshipChatId = friendshipChatId;
        this.friendshipChatMessage = friendshipChatMessage;
        this.friendshipChatSendAt = friendshipChatSendAt;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getSenderUserLogin() {
        return senderUserLogin;
    }

    public void setSenderUserLogin(String senderUserLogin) {
        this.senderUserLogin = senderUserLogin;
    }

    public String getSenderUserImageUrl() {
        return senderUserImageUrl;
    }

    public void setSenderUserImageUrl(String senderUserImageUrl) {
        this.senderUserImageUrl = senderUserImageUrl;
    }

    public Long getSenderAppUserId() {
        return senderAppUserId;
    }

    public void setSenderAppUserId(Long senderAppUserId) {
        this.senderAppUserId = senderAppUserId;
    }

    public Long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }

    public Long getFriendshipChatId() {
        return friendshipChatId;
    }

    public void setFriendshipChatId(Long friendshipChatId) {
        this.friendshipChatId = friendshipChatId;
    }

    public String getFriendshipChatMessage() {
        return friendshipChatMessage;
    }

    public void setFriendshipChatMessage(String friendshipChatMessage) {
        this.friendshipChatMessage = friendshipChatMessage;
    }

    public Instant getFriendshipChatSendAt() {
        return friendshipChatSendAt;
    }

    public void setFriendshipChatSendAt(Instant friendshipChatSendAt) {
        this.friendshipChatSendAt = friendshipChatSendAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerChatDTO)) return false;
        PlayerChatDTO that = (PlayerChatDTO) o;
        return (
            Objects.equals(senderUserId, that.senderUserId) &&
            Objects.equals(senderUserLogin, that.senderUserLogin) &&
            Objects.equals(senderUserImageUrl, that.senderUserImageUrl) &&
            Objects.equals(senderAppUserId, that.senderAppUserId) &&
            Objects.equals(friendshipId, that.friendshipId) &&
            Objects.equals(friendshipChatId, that.friendshipChatId) &&
            Objects.equals(friendshipChatMessage, that.friendshipChatMessage) &&
            Objects.equals(friendshipChatSendAt, that.friendshipChatSendAt)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            senderUserId,
            senderUserLogin,
            senderUserImageUrl,
            senderAppUserId,
            friendshipId,
            friendshipChatId,
            friendshipChatMessage,
            friendshipChatSendAt
        );
    }

    @Override
    public String toString() {
        return (
            "PlayerChatDTO{" +
            "senderUserId=" +
            senderUserId +
            ", senderUserLogin='" +
            senderUserLogin +
            '\'' +
            ", senderUserImageUrl='" +
            senderUserImageUrl +
            '\'' +
            ", senderAppUserId=" +
            senderAppUserId +
            ", friendshipId=" +
            friendshipId +
            ", friendshipChatId=" +
            friendshipChatId +
            ", friendshipChatMessage='" +
            friendshipChatMessage +
            '\'' +
            ", friendshipChatSendAt=" +
            friendshipChatSendAt +
            '}'
        );
    }
}
