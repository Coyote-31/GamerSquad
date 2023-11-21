package com.coyote.gamersquad.service.dto;

import com.coyote.gamersquad.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.coyote.gamersquad.domain.GameSub} entity.
 */
@Schema(description = "GameSub represents the subscription of an AppUser to a Game.")
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class GameSubDTO implements Serializable {

    private Long id;

    private AppUserDTO appUser;

    private GameDTO game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
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
        if (!(o instanceof GameSubDTO)) {
            return false;
        }

        GameSubDTO gameSubDTO = (GameSubDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gameSubDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameSubDTO{" +
            "id=" + getId() +
            ", appUser=" + getAppUser() +
            ", game=" + getGame() +
            "}";
    }
}
