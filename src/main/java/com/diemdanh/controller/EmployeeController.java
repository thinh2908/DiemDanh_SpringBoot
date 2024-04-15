package com.diemdanh.controller;

import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.model.Employee;
import com.diemdanh.request.EmployeeRequest;
import com.diemdanh.response.EmployeeResponse;
import com.diemdanh.service.Impl.EmployeeServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "users 0-1/"+employeeList.size());

        return ResponseEntity.ok().headers(responseHeaders).body(employeeResponses);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest){
        Employee employeeObj = employeeService.getEmployeeById(id);

        employeeObj.setJoiningDay(CoverStringToTime.cover(employeeRequest.getJoiningDay()));
        employeeObj.setName(employeeRequest.getName());
        employeeObj.setTeam(employeeRequest.getTeam());

        employeeService.updateEmployee(employeeObj);

        return ResponseEntity.ok(new EmployeeResponse(employeeObj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        Employee employeeObj = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new EmployeeResponse(employeeObj));
    }
}
