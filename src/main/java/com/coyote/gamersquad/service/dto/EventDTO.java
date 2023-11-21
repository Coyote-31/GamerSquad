package com.coyote.gamersquad.service.dto;

import com.coyote.gamersquad.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.coyote.gamersquad.domain.Event} entity.
 */
@Schema(description = "Event represents the organization of a meeting between AppUsers around a Game.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Size(max = 1024)
    private String description;

    @NotNull
    private ZonedDateTime meetingDate;

    @NotNull
    private Boolean isPrivate;

    private AppUserDTO owner;

    private GameDTO game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(ZonedDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public AppUserDTO getOwner() {
        return owner;
    }

    public void setOwner(AppUserDTO owner) {
        this.owner = owner;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", meetingDate='" + getMeetingDate() + "'" +
            ", isPrivate='" + getIsPrivate() + "'" +
            ", owner=" + getOwner() +
            ", game=" + getGame() +
            "}";
    }
}
