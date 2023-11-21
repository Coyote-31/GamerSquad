package com.coyote.gamersquad.service.dto;

import com.coyote.gamersquad.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.coyote.gamersquad.domain.EventChat} entity.
 */
@Schema(description = "EventChat represents a message of the chat in the Event.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class EventChatDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 512)
    private String message;

    @NotNull
    private Instant sendAt;

    private EventDTO event;

    private AppUserDTO appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getSendAt() {
        return sendAt;
    }

    public void setSendAt(Instant sendAt) {
        this.sendAt = sendAt;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventChatDTO)) {
            return false;
        }

        EventChatDTO eventChatDTO = (EventChatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventChatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventChatDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", sendAt='" + getSendAt() + "'" +
            ", event=" + getEvent() +
            ", appUser=" + getAppUser() +
            "}";
    }
}
