package com.bakafulteam.weplan.Exceptions;

public class AlreadyAddedFriendException extends RuntimeException {
    String friend;


    public AlreadyAddedFriendException(String friend) {
        this.friend = friend;
    }

    public AlreadyAddedFriendException(String message, Throwable cause, String friend) {
        super(message, cause);
        this.friend = friend;
    }

    public AlreadyAddedFriendException(Throwable cause, String friend) {
        super(cause);
        this.friend = friend;
    }

    public AlreadyAddedFriendException(String message, String friend) {
        super(message);
        this.friend = friend;
    }

    protected AlreadyAddedFriendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String friend) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.friend = friend;
    }


    @Override
    public String getMessage() {
        return "You already added " + this.friend + " as your friend";

    }

}
