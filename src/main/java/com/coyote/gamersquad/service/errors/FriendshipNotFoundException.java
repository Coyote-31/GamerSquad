package com.coyote.gamersquad.service.errors;

public class FriendshipNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FriendshipNotFoundException(Long friendshipId) {
        super("Friendship not found with id : " + friendshipId);
    }

    public FriendshipNotFoundException(Long ownerId, Long receiverId) {
        super("Friendship not found with AppUser owner id : " + ownerId + " and AppUser receiver id : " + receiverId);
    }

    public FriendshipNotFoundException(Long appUserId, String userLogin) {
        super("Friendship not found between appUser id : " + appUserId + " and User login : " + userLogin);
    }
}
