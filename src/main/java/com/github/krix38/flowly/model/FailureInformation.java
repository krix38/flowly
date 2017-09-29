package com.github.krix38.flowly.model;

/**
 * Created by krix on 29.09.2017.
 */
public class FailureInformation {

    private Throwable exception;

    public FailureInformation(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}
