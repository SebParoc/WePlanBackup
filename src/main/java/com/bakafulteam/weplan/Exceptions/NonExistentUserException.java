package com.bakafulteam.weplan.Exceptions;

public class NonExistentUserException extends RuntimeException{

    String username;


    public NonExistentUserException(String username) {
        this.username= username;
    }

    public NonExistentUserException(String message, Throwable cause, String username) {
        super(message, cause);
        this.username = username;
    }

    public NonExistentUserException(Throwable cause, String username) {
        super(cause);
        this.username = username;
    }

    public NonExistentUserException(String message, String username) {
        super(message);
        this.username = username;
    }

    protected NonExistentUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String username) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.username = username;
    }


    @Override
    public String getMessage() {
        return "The user "+ this.username + " you tried to add does not exist.";
    }
}
