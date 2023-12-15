package com.coyote.gamersquad.service.dto;

import com.coyote.gamersquad.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.coyote.gamersquad.domain.FriendshipChat} entity.
 */
@Schema(description = "FriendshipChat represents a message of the chat between two AppUsers friends.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class FriendshipChatDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 512)
    private String message;

    @NotNull
    private Instant sendAt;

    private FriendshipDTO friendship;

    private AppUserDTO sender;

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

    public FriendshipDTO getFriendship() {
        return friendship;
    }

    public void setFriendship(FriendshipDTO friendship) {
        this.friendship = friendship;
    }

    public AppUserDTO getSender() {
        return sender;
    }

    public void setSender(AppUserDTO sender) {
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendshipChatDTO)) {
            return false;
        }

        FriendshipChatDTO friendshipChatDTO = (FriendshipChatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, friendshipChatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendshipChatDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", sendAt='" + getSendAt() + "'" +
            ", friendship=" + getFriendship() +
            ", sender=" + getSender() +
            "}";
    }
}
