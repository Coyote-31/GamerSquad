package com.coyote.gamersquad.service.dto.projection;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO to display a player in the view with friendship info.
 */
public class PlayerFriendshipDTO implements Serializable {

    private Long userId;

    private String userLogin;

    private String userImageUrl;

    private Long appUserId;

    private Long friendshipId;

    private boolean accepted;

    private boolean owned;

    private boolean received;

    public PlayerFriendshipDTO(
        Long userId,
        String userLogin,
        String userImageUrl,
        Long appUserId,
        Long friendshipId,
        boolean accepted,
        boolean owned,
        boolean received
    ) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userImageUrl = userImageUrl;
        this.appUserId = appUserId;
        this.friendshipId = friendshipId;
        this.accepted = accepted;
        this.owned = owned;
        this.received = received;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerFriendshipDTO)) return false;
        PlayerFriendshipDTO that = (PlayerFriendshipDTO) o;
        return (
            accepted == that.accepted &&
            owned == that.owned &&
            received == that.received &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userLogin, that.userLogin) &&
            Objects.equals(userImageUrl, that.userImageUrl) &&
            Objects.equals(appUserId, that.appUserId) &&
            Objects.equals(friendshipId, that.friendshipId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userLogin, userImageUrl, appUserId, friendshipId, accepted, owned, received);
    }

    @Override
    public String toString() {
        return (
            "PlayerFriendshipDTO{" +
            "userId=" +
            userId +
            ", userLogin='" +
            userLogin +
            '\'' +
            ", userImageUrl='" +
            userImageUrl +
            '\'' +
            ", appUserId=" +
            appUserId +
            ", friendshipId=" +
            friendshipId +
            ", accepted=" +
            accepted +
            ", owned=" +
            owned +
            ", received=" +
            received +
            '}'
        );
    }
}
