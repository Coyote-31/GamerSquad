package com.coyote.gamersquad.service.dto.form;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FriendMessageDTO implements Serializable {

    @NotBlank
    @Size(max = 512)
    private String message;

    public FriendMessageDTO() {
        // Empty constructor needed for Jackson.
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendMessageDTO)) return false;
        FriendMessageDTO that = (FriendMessageDTO) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "FriendMessageDTO{" + "message='" + message + '\'' + '}';
    }
}
