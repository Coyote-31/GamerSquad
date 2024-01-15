package com.coyote.gamersquad.service.errors;

public class EventSubNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EventSubNotFoundException(Long appUserId, Long eventId) {
        super("EventSub not found for appUserId : " + appUserId + " and eventId : " + eventId);
    }
}
