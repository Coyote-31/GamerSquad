package com.coyote.gamersquad.service.dto.projection;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO to display a player in the event view.
 */
public class EventPlayerDTO implements Serializable {

    private Long userId;

    private String userLogin;

    private String userImageUrl;

    private Long appUserId;

    private Long eventId;

    private Long eventSubId;

    private Boolean accepted;

    public EventPlayerDTO(
        Long userId,
        String userLogin,
        String userImageUrl,
        Long appUserId,
        Long eventId,
        Long eventSubId,
        Boolean accepted
    ) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userImageUrl = userImageUrl;
        this.appUserId = appUserId;
        this.eventId = eventId;
        this.eventSubId = eventSubId;
        this.accepted = accepted;
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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventSubId() {
        return eventSubId;
    }

    public void setEventSubId(Long eventSubId) {
        this.eventSubId = eventSubId;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventPlayerDTO)) return false;
        EventPlayerDTO that = (EventPlayerDTO) o;
        return (
            Objects.equals(userId, that.userId) &&
            Objects.equals(userLogin, that.userLogin) &&
            Objects.equals(userImageUrl, that.userImageUrl) &&
            Objects.equals(appUserId, that.appUserId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(eventSubId, that.eventSubId) &&
            Objects.equals(accepted, that.accepted)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userLogin, userImageUrl, appUserId, eventId, eventSubId, accepted);
    }

    @Override
    public String toString() {
        return (
            "EventPlayerDTO{" +
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
            ", eventId=" +
            eventId +
            ", eventSubId=" +
            eventSubId +
            ", accepted=" +
            accepted +
            '}'
        );
    }
}
