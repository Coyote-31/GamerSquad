package com.coyote.gamersquad.service.dto.form;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EventCreateDTO implements Serializable {

    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 1024)
    private String description;

    @NotNull
    private ZonedDateTime meetingDate;

    @NotNull
    private Boolean isPrivate;

    public EventCreateDTO() {
        // Empty constructor needed for Jackson.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventCreateDTO)) return false;
        EventCreateDTO that = (EventCreateDTO) o;
        return (
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(meetingDate, that.meetingDate) &&
            Objects.equals(isPrivate, that.isPrivate)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, meetingDate, isPrivate);
    }

    @Override
    public String toString() {
        return (
            "EventCreateDTO{" +
            "title='" +
            title +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", meetingDate=" +
            meetingDate +
            ", isPrivate=" +
            isPrivate +
            '}'
        );
    }
}
