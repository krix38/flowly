package com.github.krix38.flowly.model;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by krix on 29.09.2017.
 */

@XmlTransient
public class FailureInformation {

    private Throwable exception;

    public FailureInformation() {
    }

    public FailureInformation(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}
