package com.diemdanh.service;

import com.diemdanh.model.Employee;

import java.util.List;

public interface IEmployee {
    public Employee createEmployee(Employee employee);
    public List<Employee> listEmployee();
    public Employee getEmployeeById(Long id);
}
