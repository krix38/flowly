package com.github.krix38.flowly.exampleAction;

import com.github.krix38.flowly.annotation.FlowAction;
import com.github.krix38.flowly.exampleModel.Employee;

/**
 * Created by krix on 30.09.2017.
 */
public class ActionThrowingExecutionException {

    @FlowAction
    public Integer badMethod(Employee employee){
        return 5;
    }

}
