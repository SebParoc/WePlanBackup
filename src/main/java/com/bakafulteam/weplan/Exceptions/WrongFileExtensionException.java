package com.bakafulteam.weplan.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Not allowed")

public class WrongFileExtensionException extends RuntimeException{

    String extension;

    public WrongFileExtensionException(String extension) {
        this.extension = extension;
    }

    public WrongFileExtensionException(String message, String extension) {
        super(message);
        this.extension = extension;
    }

    public WrongFileExtensionException(String message, Throwable cause, String extension) {
        super(message, cause);
        this.extension = extension;
    }

    public WrongFileExtensionException(Throwable cause, String extension) {
        super(cause);
        this.extension = extension;
    }

    public WrongFileExtensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String extension) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "The selected file extension" + this.extension+ "is not allowed. Only .csv files are allowed";

    }
}
