package com.coyote.gamersquad.service.dto;

import com.coyote.gamersquad.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.coyote.gamersquad.domain.EventSub} entity.
 */
@Schema(description = "EventSub represents subscription of an AppUser to this Event.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class EventSubDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean isAccepted;

    private EventDTO event;

    private AppUserDTO appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
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
        if (!(o instanceof EventSubDTO)) {
            return false;
        }

        EventSubDTO eventSubDTO = (EventSubDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventSubDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventSubDTO{" +
            "id=" + getId() +
            ", isAccepted='" + getIsAccepted() + "'" +
            ", event=" + getEvent() +
            ", appUser=" + getAppUser() +
            "}";
    }
}
