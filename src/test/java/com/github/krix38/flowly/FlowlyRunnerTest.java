package com.github.krix38.flowly;

import com.github.krix38.flowly.exampleAction.*;
import com.github.krix38.flowly.exampleModel.Employee;
import com.github.krix38.flowly.exampleModel.Manager;
import com.github.krix38.flowly.exception.ActionExecutionException;
import com.github.krix38.flowly.model.AbstractModel;
import com.github.krix38.flowly.register.FlowRegister;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test(expected = ActionExecutionException.class)
    public void testRunnerActionWithoutDefaultConstructor() throws ActionExecutionException {


        FlowRegister flowRegister = new FlowRegister();

        flowRegister.register(ActionWithoutDefaultConstructor.class);

        flowRegister.run(employee);

    }

    @Test(expected = ActionExecutionException.class)
    public void testRunnerExecutionException() throws ActionExecutionException {


        FlowRegister flowRegister = new FlowRegister();

        flowRegister.register(new RaiseSalary(200));
        flowRegister.register(UpperCaseNames.class);
        flowRegister.register(ActionThrowingExecutionException.class);
        flowRegister.register(LowerSurname.class);



        flowRegister.run(employee);

    }


    @Test
    public void testRunnerModelTransformation() throws ActionExecutionException {
        FlowRegister flowRegister = new FlowRegister();

        flowRegister.register(new RaiseSalary(200));
        flowRegister.register(UpperCaseNames.class);
        flowRegister.register(LowerSurname.class);
        flowRegister.register(MapEmployeeToManager.class);

        Manager manager = (Manager) flowRegister.run(employee);

        assertEquals("JAN kowalski", manager.getNames());
        assertEquals(new Integer(300), manager.getSalary());
    }

    @Test
    public void testRunnerModelTransformationUnmatchingModelOmmited() throws ActionExecutionException {
        FlowRegister flowRegister = new FlowRegister();

        flowRegister.register(new RaiseSalary(200));
        flowRegister.register(UpperCaseNames.class);
        flowRegister.register(LowerSurname.class);
        flowRegister.register(MapEmployeeToManager.class);
        flowRegister.register(UpperCaseNames.class);


        Manager manager = (Manager) flowRegister.run(employee);

        assertEquals("JAN kowalski", manager.getNames());
        assertEquals(new Integer(300), manager.getSalary());
    }

    @Test
    public void testRunner() throws ActionExecutionException {

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
    public void testFailure() throws ActionExecutionException {

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

    @Test
    public void testRunnerForCollectionInput() throws ActionExecutionException {

        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee("Mariusz", "Akwariusz", 100));
        employees.add(new Employee("Zbigniew", "Kowalski", 200));
        employees.add(new Employee("Tyrone", "Slothrop", 50));

        FlowRegister flowRegister = new FlowRegister();
        flowRegister.register(new RaiseSalary(200));
        flowRegister.register(UpperCaseNames.class);

        List<AbstractModel> transformedEmployees = flowRegister.runForAll(employees);

        assertEquals("MARIUSZ", ((Employee)transformedEmployees.get(0)).getFirstName());
        assertEquals("AKWARIUSZ", ((Employee)transformedEmployees.get(0)).getLastName());
        assertEquals(new Integer(300), ((Employee)transformedEmployees.get(0)).getSalary());

        assertEquals("ZBIGNIEW", ((Employee)transformedEmployees.get(1)).getFirstName());
        assertEquals("KOWALSKI", ((Employee)transformedEmployees.get(1)).getLastName());
        assertEquals(new Integer(400), ((Employee)transformedEmployees.get(1)).getSalary());

        assertEquals("TYRONE", ((Employee)transformedEmployees.get(2)).getFirstName());
        assertEquals("SLOTHROP", ((Employee)transformedEmployees.get(2)).getLastName());
        assertEquals(new Integer(250), ((Employee)transformedEmployees.get(2)).getSalary());

    }

}
