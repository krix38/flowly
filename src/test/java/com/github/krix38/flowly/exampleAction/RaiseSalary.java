package com.github.krix38.flowly.exampleAction;

import com.github.krix38.flowly.annotation.FlowAction;
import com.github.krix38.flowly.exampleModel.Employee;
import com.github.krix38.flowly.model.AbstractModel;

/**
 * Created by krix on 29.09.2017.
 */
public class RaiseSalary {

    public RaiseSalary(Integer raiseBy) {
        this.raiseBy = raiseBy;
    }

    private Integer raiseBy;

    @FlowAction
    public void raise(Employee employee){
        employee.setSalary(employee.getSalary() + raiseBy);
    }

}
