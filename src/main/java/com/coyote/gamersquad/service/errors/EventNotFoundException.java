package com.coyote.gamersquad.service.errors;

public class EventNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EventNotFoundException(Long eventId) {
        super("Event not found with id : " + eventId);
    }
}
