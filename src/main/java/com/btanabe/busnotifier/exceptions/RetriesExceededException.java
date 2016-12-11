package com.btanabe.busnotifier.exceptions;

/**
 * Created by Brian on 12/4/16.
 */
public class RetriesExceededException extends Exception {

    public RetriesExceededException(Throwable cause) {
        super(cause);
    }
}
