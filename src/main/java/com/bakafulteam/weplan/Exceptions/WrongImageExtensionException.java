package com.bakafulteam.weplan.Exceptions;

import org.springframework.web.multipart.MultipartFile;

public class WrongImageExtensionException extends RuntimeException{

    String extension ;

    public WrongImageExtensionException(String extension) {
        this.extension = extension;
    }

    public WrongImageExtensionException(String message, String extension) {
        super(message);
        this.extension = extension;
    }

    public WrongImageExtensionException(String message, Throwable cause, String extension) {
        super(message, cause);
        this.extension = extension;
    }

    public WrongImageExtensionException(Throwable cause, String extension) {
        super(cause);
        this.extension = extension;
    }

    public WrongImageExtensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String extension) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.extension = extension;
    }

    @Override
    public String getMessage() {
        return "The supplied extension" + this.extension + " is not allowed. Only .png or .jpg files are allowed.";
    }

}
