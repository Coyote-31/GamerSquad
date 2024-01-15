package com.coyote.gamersquad.service.errors;

public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GameNotFoundException(Long gameId) {
        super("Game not found with id : " + gameId);
    }
}
