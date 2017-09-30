package com.github.krix38.flowly.exampleAction;

import com.github.krix38.flowly.annotation.FlowAction;
import com.github.krix38.flowly.exampleModel.Employee;
import com.github.krix38.flowly.exampleModel.Manager;

/**
 * Created by krix on 30.09.2017.
 */
public class MapEmployeeToManager {


    @FlowAction
    public Manager map(Employee employee){
        return new Manager(employee.getFirstName() + " " + employee.getLastName(), employee.getSalary());
    }

}
