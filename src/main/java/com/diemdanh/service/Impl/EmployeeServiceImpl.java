package com.diemdanh.service.Impl;

import com.diemdanh.model.Employee;
import com.diemdanh.repo.EmployeeRepository;
import com.diemdanh.service.IEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployee {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Employee employeeObj = employeeRepository.save(employee);
        return employeeObj;
    }

    @Override
    public List<Employee> listEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList;
    }
}
