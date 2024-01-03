package com.coyote.gamersquad.service.dto.form;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EventMessageDTO implements Serializable {

    @NotBlank
    @Size(max = 512)
    private String message;

    public EventMessageDTO() {
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
        if (!(o instanceof EventMessageDTO)) return false;
        EventMessageDTO that = (EventMessageDTO) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "EventMessageDTO{" + "message='" + message + '\'' + '}';
    }
}
