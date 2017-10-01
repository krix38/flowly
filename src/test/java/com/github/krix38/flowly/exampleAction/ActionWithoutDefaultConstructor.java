package com.github.krix38.flowly.exampleAction;

import com.github.krix38.flowly.annotation.FlowAction;
import com.github.krix38.flowly.exampleModel.Employee;

/**
 * Created by krix on 01.10.2017.
 */
public class ActionWithoutDefaultConstructor {

    private Integer exampleField;

    public ActionWithoutDefaultConstructor(Integer exampleField) {
        this.exampleField = exampleField;
    }

    @FlowAction
    public void test(Employee employee){

    }
}
