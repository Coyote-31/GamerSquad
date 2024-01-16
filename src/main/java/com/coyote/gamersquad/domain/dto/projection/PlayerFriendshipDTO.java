package com.coyote.gamersquad.domain.dto.projection;

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

    private Boolean accepted;

    private Boolean owned;

    private Boolean received;

    public PlayerFriendshipDTO(
        Long userId,
        String userLogin,
        String userImageUrl,
        Long appUserId,
        Long friendshipId,
        Boolean accepted,
        Boolean owned,
        Boolean received
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

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean isOwned() {
        return owned;
    }

    public void setOwned(Boolean owned) {
        this.owned = owned;
    }

    public Boolean isReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
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
