package com.github.krix38.flowly;

import com.github.krix38.flowly.exampleAction.FailingAction;
import com.github.krix38.flowly.exampleAction.LowerSurname;
import com.github.krix38.flowly.exampleAction.RaiseSalary;
import com.github.krix38.flowly.exampleAction.UpperCaseNames;
import com.github.krix38.flowly.exampleModel.Employee;
import com.github.krix38.flowly.register.FlowRegister;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 29.09.2017.
 */
public class FlowlyRunnerTest {

    private Employee employee;

    @Before
    public void setup(){
        this.employee = new Employee("jan", "kowalski", 100);
    }


    @Test
    public void testRunner(){

        FlowRegister flowRegister = new FlowRegister();

        flowRegister.register(new RaiseSalary(200));
        flowRegister.register(UpperCaseNames.class);
        flowRegister.register(LowerSurname.class);

        flowRegister.run(employee);

        assertEquals("JAN", employee.getFirstName());
        assertEquals("kowalski", employee.getLastName());
        assertEquals(new Integer(300), employee.getSalary());

    }

    @Test
    public void testFailure(){

        FlowRegister flowRegister = new FlowRegister();

        flowRegister.register(new RaiseSalary(200));
        flowRegister.register(UpperCaseNames.class);
        flowRegister.register(FailingAction.class);
        flowRegister.register(LowerSurname.class);

        flowRegister.run(employee);

        assertEquals("JAN", employee.getFirstName());
        assertEquals("KOWALSKI", employee.getLastName());
        assertEquals(new Integer(300), employee.getSalary());
        assertEquals(NullPointerException.class, employee.getFailureInformation().getException().getClass());

    }
}
