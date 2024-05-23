package com.diemdanh.service;

import com.diemdanh.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployee {
    public Employee createEmployee(Employee employee);
    public List<Employee> listEmployee();
    public List<Employee> listEmployeeByIds(List<Long> listId);
    public Employee getEmployeeById(Long id);
    public Employee updateEmployee(Employee employee);
    public Employee deleteEmployee(Long id);
    public Page<Employee> findAll(Pageable pageable);
    public Page<Employee> findAllByName(String name, Pageable page);
    public List<Employee> findEmployeeById(Long id);
}
