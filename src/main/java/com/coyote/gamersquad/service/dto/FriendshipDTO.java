package com.coyote.gamersquad.service.dto;

import com.coyote.gamersquad.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.coyote.gamersquad.domain.Friendship} entity.
 */
@Schema(description = "Friendship represents the friendship relation between two AppUsers.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class FriendshipDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean isAccepted;

    private AppUserDTO appUserOwner;

    private AppUserDTO appUserReceiver;

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

    public AppUserDTO getAppUserOwner() {
        return appUserOwner;
    }

    public void setAppUserOwner(AppUserDTO appUserOwner) {
        this.appUserOwner = appUserOwner;
    }

    public AppUserDTO getAppUserReceiver() {
        return appUserReceiver;
    }

    public void setAppUserReceiver(AppUserDTO appUserReceiver) {
        this.appUserReceiver = appUserReceiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendshipDTO)) {
            return false;
        }

        FriendshipDTO friendshipDTO = (FriendshipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, friendshipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendshipDTO{" +
            "id=" + getId() +
            ", isAccepted='" + getIsAccepted() + "'" +
            ", appUserOwner=" + getAppUserOwner() +
            ", appUserReceiver=" + getAppUserReceiver() +
            "}";
    }
}
