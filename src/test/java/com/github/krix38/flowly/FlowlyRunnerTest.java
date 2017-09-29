package com.github.krix38.flowly;

import com.github.krix38.flowly.exampleAction.RaiseSalary;
import com.github.krix38.flowly.exampleAction.UpperCaseNames;
import com.github.krix38.flowly.exampleModel.Employee;
import com.github.krix38.flowly.register.FlowRegister;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 29.09.2017.
 */
public class FlowlyRunnerTest {

    @Test
    public void testRunner(){

        Employee employee = new Employee("jan", "kowalski", 100);

        FlowRegister flowRegister = new FlowRegister();

        flowRegister.register(RaiseSalary.class);
        flowRegister.register(UpperCaseNames.class);

        flowRegister.run(employee);

        assertEquals("JAN", employee.getFirstName());
        assertEquals("KOWALSKI", employee.getLastName());
        assertEquals(new Integer(200), employee.getSalary());

    }
}
