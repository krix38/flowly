package com.github.krix38.flowly.exampleAction;

import com.github.krix38.flowly.annotation.FlowAction;
import com.github.krix38.flowly.exampleModel.Employee;

/**
 * Created by krix on 29.09.2017.
 */
public class UpperCaseNames {

    @FlowAction
    public void upper(Employee employee){
        employee.setFirstName(employee.getFirstName().toUpperCase());
        employee.setLastName(employee.getLastName().toUpperCase());
    }

}
