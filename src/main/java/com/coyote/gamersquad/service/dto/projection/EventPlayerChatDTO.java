package com.coyote.gamersquad.service.dto.projection;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO to display an event chat message in the view.
 */
public class EventPlayerChatDTO implements Serializable {

    private Long senderUserId;

    private String senderUserLogin;

    private String senderUserImageUrl;

    private Long senderAppUserId;

    private Long eventId;

    private Long eventChatId;

    private String eventChatMessage;

    private Instant eventChatSendAt;

    public EventPlayerChatDTO(
        Long senderUserId,
        String senderUserLogin,
        String senderUserImageUrl,
        Long senderAppUserId,
        Long eventId,
        Long eventChatId,
        String eventChatMessage,
        Instant eventChatSendAt
    ) {
        this.senderUserId = senderUserId;
        this.senderUserLogin = senderUserLogin;
        this.senderUserImageUrl = senderUserImageUrl;
        this.senderAppUserId = senderAppUserId;
        this.eventId = eventId;
        this.eventChatId = eventChatId;
        this.eventChatMessage = eventChatMessage;
        this.eventChatSendAt = eventChatSendAt;
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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventChatId() {
        return eventChatId;
    }

    public void setEventChatId(Long eventChatId) {
        this.eventChatId = eventChatId;
    }

    public String getEventChatMessage() {
        return eventChatMessage;
    }

    public void setEventChatMessage(String eventChatMessage) {
        this.eventChatMessage = eventChatMessage;
    }

    public Instant getEventChatSendAt() {
        return eventChatSendAt;
    }

    public void setEventChatSendAt(Instant eventChatSendAt) {
        this.eventChatSendAt = eventChatSendAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventPlayerChatDTO)) return false;
        EventPlayerChatDTO that = (EventPlayerChatDTO) o;
        return (
            Objects.equals(senderUserId, that.senderUserId) &&
            Objects.equals(senderUserLogin, that.senderUserLogin) &&
            Objects.equals(senderUserImageUrl, that.senderUserImageUrl) &&
            Objects.equals(senderAppUserId, that.senderAppUserId) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(eventChatId, that.eventChatId) &&
            Objects.equals(eventChatMessage, that.eventChatMessage) &&
            Objects.equals(eventChatSendAt, that.eventChatSendAt)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            senderUserId,
            senderUserLogin,
            senderUserImageUrl,
            senderAppUserId,
            eventId,
            eventChatId,
            eventChatMessage,
            eventChatSendAt
        );
    }

    @Override
    public String toString() {
        return (
            "EventChatDTO{" +
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
            ", eventId=" +
            eventId +
            ", eventChatId=" +
            eventChatId +
            ", eventChatMessage='" +
            eventChatMessage +
            '\'' +
            ", eventChatSendAt=" +
            eventChatSendAt +
            '}'
        );
    }
}
