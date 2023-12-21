package com.coyote.gamersquad.service.dto.projection;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class EventDetailDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private ZonedDateTime meetingDate;

    private Boolean isPrivate;

    private String ownerLogin;

    private String ownerImgUrl;

    private Long gameId;

    private String gameTitle;

    private String gameImgUrl;

    public EventDetailDTO(
        Long id,
        String title,
        String description,
        ZonedDateTime meetingDate,
        Boolean isPrivate,
        String ownerLogin,
        String ownerImgUrl,
        Long gameId,
        String gameTitle,
        String gameImgUrl
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.meetingDate = meetingDate;
        this.isPrivate = isPrivate;
        this.ownerLogin = ownerLogin;
        this.ownerImgUrl = ownerImgUrl;
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.gameImgUrl = gameImgUrl;
    }

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

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getOwnerImgUrl() {
        return ownerImgUrl;
    }

    public void setOwnerImgUrl(String ownerImgUrl) {
        this.ownerImgUrl = ownerImgUrl;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameImgUrl() {
        return gameImgUrl;
    }

    public void setGameImgUrl(String gameImgUrl) {
        this.gameImgUrl = gameImgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventDetailDTO)) return false;
        EventDetailDTO that = (EventDetailDTO) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(meetingDate, that.meetingDate) &&
            Objects.equals(isPrivate, that.isPrivate) &&
            Objects.equals(ownerLogin, that.ownerLogin) &&
            Objects.equals(ownerImgUrl, that.ownerImgUrl) &&
            Objects.equals(gameId, that.gameId) &&
            Objects.equals(gameTitle, that.gameTitle) &&
            Objects.equals(gameImgUrl, that.gameImgUrl)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, meetingDate, isPrivate, ownerLogin, ownerImgUrl, gameId, gameTitle, gameImgUrl);
    }
}
