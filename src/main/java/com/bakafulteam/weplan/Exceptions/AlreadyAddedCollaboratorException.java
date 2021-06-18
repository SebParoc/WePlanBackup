package com.bakafulteam.weplan.Exceptions;

public class AlreadyAddedCollaboratorException extends RuntimeException {
    String collaborator;


    public AlreadyAddedCollaboratorException(String collaborator) {
        this.collaborator = collaborator;
    }

    public AlreadyAddedCollaboratorException(String message, Throwable cause, String collaborator) {
        super(message, cause);
        this.collaborator = collaborator;
    }

    public AlreadyAddedCollaboratorException(Throwable cause, String collaborator) {
        super(cause);
        this.collaborator = collaborator;
    }

    public AlreadyAddedCollaboratorException(String message, String collaborator) {
        super(message);
        this.collaborator = collaborator;
    }

    protected AlreadyAddedCollaboratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String collaborator) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.collaborator = collaborator;
    }


    @Override
    public String getMessage() {
        return "You already added " + this.collaborator + " as your collaborator";

    }
}
