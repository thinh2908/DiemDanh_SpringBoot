package com.diemdanh.service.Impl;

import com.diemdanh.model.Employee;
import com.diemdanh.repo.EmployeeRepository;
import com.diemdanh.response.EmployeeResponse;
import com.diemdanh.service.IEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Employee updateEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee deleteEmployee(Long id) {
        Employee employeeObj = employeeRepository.findById(id).get();
        employeeRepository.deleteById(id);
        return employeeObj;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
       return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> findAllByName(String name, Pageable page) {
        return employeeRepository.findByNameContaining(name,page);
    }

    @Override
    public List<Employee> findEmployeeById(Long id) {
        List<Employee> employeeList= null;
        employeeList.add(employeeRepository.findById(id).get());
        return employeeList;
    }

    @Override
    public List<Employee> listEmployeeByIds(List<Long> listId) {
        List<Employee> employeeList = employeeRepository.findAllById(listId);
        return employeeList;
    }

    @Override
    public Long countAll() {
        return employeeRepository.count();
    }
}
