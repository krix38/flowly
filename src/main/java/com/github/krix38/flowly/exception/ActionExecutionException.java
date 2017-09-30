package com.github.krix38.flowly.exception;

/**
 * Created by krix on 29.09.2017.
 */
public class ActionExecutionException extends Exception {
    public ActionExecutionException() {
    }

    public ActionExecutionException(Throwable cause) {
        super(cause);
    }

    public ActionExecutionException(String message) {
        super(message);
    }

    public ActionExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
