package com.github.krix38.flowly.model;

/**
 * Created by krix on 29.09.2017.
 */
public class FailureInformation {

    private Exception exception;

    public FailureInformation(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
