package com.github.krix38.flowly.model;

import java.util.Optional;

/**
 * Created by krix on 29.09.2017.
 */

public abstract class AbstractModel {

    private Optional<FailureInformation> failureInformation = Optional.empty();

    public FailureInformation getFailureInformation() {
        return failureInformation.get();
    }

    public void setFailureInformation(FailureInformation failureInformation) {
        this.failureInformation = Optional.of(failureInformation);
    }

    public Boolean hasFailed(){
        return this.failureInformation.isPresent();
    }
}
