package com.github.krix38.flowly.model;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Optional;

/**
 * Created by krix on 29.09.2017.
 */

@XmlTransient
public abstract class AbstractModel {

    @XmlTransient
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
