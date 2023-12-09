package com.coyote.gamersquad.service.dto.projection;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO used to test projection with a repository.
 * Used only for debugging
 */
public class TestDTO implements Serializable {

    private Long id;

    private String login;

    private boolean active;

    public TestDTO(Long xId, String xLogin, boolean active) {
        this.id = xId;
        this.login = xLogin;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestDTO)) return false;
        TestDTO testDTO = (TestDTO) o;
        return Objects.equals(id, testDTO.id) && Objects.equals(login, testDTO.login) && Objects.equals(active, testDTO.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, active);
    }
}
