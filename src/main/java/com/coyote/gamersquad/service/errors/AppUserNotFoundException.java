package com.coyote.gamersquad.service.errors;

public class AppUserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AppUserNotFoundException(String login) {
        super("AppUser not found with login : " + login);
    }

    public AppUserNotFoundException(Long appUserId) {
        super("AppUser not found with id : " + appUserId);
    }
}
