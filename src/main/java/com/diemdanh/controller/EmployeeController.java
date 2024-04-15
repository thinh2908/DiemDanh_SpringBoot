package com.diemdanh.controller;

import com.diemdanh.model.Employee;
import com.diemdanh.request.EmployeeRequest;
import com.diemdanh.response.EmployeeResponse;
import com.diemdanh.service.Impl.EmployeeServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService ;

    @GetMapping("")
    public ResponseEntity<?> listAllEmployee(){
        List<Employee> employeeList = employeeService.listEmployee();
        List<EmployeeResponse> employeeResponses = employeeList.stream()
                                            .map(EmployeeResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(employeeResponses);
    }

    @PostMapping("")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employeeObj = new Employee(employeeRequest);
        Employee employee = employeeService.createEmployee(employeeObj);
        //return ResponseEntity.ok(employee);
        return  ResponseEntity.accepted().body(new EmployeeResponse(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
        Employee employeeObj = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(new EmployeeResponse(employeeObj));
    }
}
