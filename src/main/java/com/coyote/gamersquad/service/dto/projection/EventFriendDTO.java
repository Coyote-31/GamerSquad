package com.coyote.gamersquad.service.dto.projection;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO to display a friend in the event view.
 */
public class EventFriendDTO implements Serializable {

    private Long userId;

    private String userLogin;

    private String userImageUrl;

    private Long appUserId;

    public EventFriendDTO(Long userId, String userLogin, String userImageUrl, Long appUserId) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userImageUrl = userImageUrl;
        this.appUserId = appUserId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventFriendDTO)) return false;
        EventFriendDTO that = (EventFriendDTO) o;
        return (
            Objects.equals(userId, that.userId) &&
            Objects.equals(userLogin, that.userLogin) &&
            Objects.equals(userImageUrl, that.userImageUrl) &&
            Objects.equals(appUserId, that.appUserId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userLogin, userImageUrl, appUserId);
    }

    @Override
    public String toString() {
        return (
            "EventFriendDTO{" +
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
            '}'
        );
    }
}
