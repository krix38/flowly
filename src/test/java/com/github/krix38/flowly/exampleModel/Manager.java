package com.github.krix38.flowly.exampleModel;

import com.github.krix38.flowly.model.AbstractModel;

/**
 * Created by krix on 30.09.2017.
 */
public class Manager extends AbstractModel {

    private String names;

    private Integer salary;

    public Manager(String names, Integer salary) {
        this.names = names;
        this.salary = salary;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
