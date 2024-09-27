package com.diemdanh.controller;

import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.base.BaseFunction;
import com.diemdanh.base.Constants;
import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.base.MessageString;
import com.diemdanh.factory.FilesStorageService;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Message;
import com.diemdanh.model.Users;
import com.diemdanh.request.EmployeeRequest;
import com.diemdanh.response.EmployeeResponse;
import com.diemdanh.service.Impl.EmployeeServiceImpl;

import com.diemdanh.service.Impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService ;

    @Autowired
    FilesStorageService filesStorageService;

    @Autowired
    MessageServiceImpl messageService;

    /*@GetMapping("")
    public ResponseEntity<?> listAllEmployee(){
        List<Employee> employeeList = employeeService.listEmployee();
        List<EmployeeResponse> employeeResponses = employeeList.stream()
                                            .map(EmployeeResponse::new).collect(Collectors.toList());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "users 0-1/"+employeeList.size());

        return ResponseEntity.ok().headers(responseHeaders).body(employeeResponses);
    }*/

    @GetMapping("")
    public ResponseEntity<?> listAllEmployee(@RequestParam(required = false,value = "sort") String sort,
                                             @RequestParam(required = false,value = "range") String range,
                                             @RequestParam(required = false,value = "filter") String filter
    ){

            Pageable paging = null;
            Sort sortObject = Sort.by(Sort.Direction.DESC,"id");

            List<Integer> rangeList = BaseFunction.stringToListInteger(range);
            List<String> sortList = BaseFunction.stringToListString(sort);

            //Xu ly body
            if (sort != null && !sort.isEmpty()) {
                sortObject = Sort.by(sortList.get(1).equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,sortList.get(0));
            }


            if (rangeList != null && rangeList.size() == 2) {
                paging = PageRequest.of(Math.round(rangeList.get(0)/(rangeList.get(1) - rangeList.get(0) + 1)),
                                        rangeList.get(1) - rangeList.get(0) + 1,
                                            sortObject);
            }
            Page<Employee> pageEmployee;
            if (filter != "{}" && BaseFunction.stringToMap(filter).containsKey("name")) {
                pageEmployee = employeeService.findAllByName(BaseFunction.stringToMap(filter).get("name"), paging);
            }else
                pageEmployee = employeeService.findAll(paging);

            List<Employee> employeeList;
            employeeList = pageEmployee.getContent();

            List<EmployeeResponse> employeeResponses = employeeList.stream()
                .map(EmployeeResponse::new).collect(Collectors.toList());


            //Xu ly header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Range",
                "employee "+ rangeList.get(0) + "-" + rangeList.get(1) + "/" + pageEmployee.getTotalElements());
            return  ResponseEntity.ok().headers(responseHeaders).body(employeeResponses);


    }
    @GetMapping("/getManyById")
    public ResponseEntity<?> listAllEmployeeFilterId(@RequestParam(required = false,value = "id") String ids
    ){

        List<Employee> employeeList;
        employeeList = employeeService.listEmployeeByIds(BaseFunction.convertStringToListLong(ids));
        List<EmployeeResponse> employeeResponses = employeeList.stream()
                .map(EmployeeResponse::new).collect(Collectors.toList());


        //Xu ly header
        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Content-Range",
//                "employee "+ rangeList.get(0) + "-" + rangeList.get(1) + "/" + employeeService.listEmployee().size());
        return  ResponseEntity.ok().headers(responseHeaders).body(employeeResponses);


    }
    @PostMapping(value = "")
    public ResponseEntity<?> createEmployee(@ModelAttribute EmployeeRequest employeeRequest) {
        Employee employeeObj = new Employee(employeeRequest);
        Employee employee = employeeService.createEmployee(employeeObj);

        String avatarDir = "uploads/" + "User" +employee.getId() + ".jpg";
        String avatarUrl = "/files/images/" + "User"+employee.getId() + ".jpg";
        boolean existed = filesStorageService.delete(avatarDir);
        filesStorageService.saveAs(employeeRequest.getAvatar(), avatarDir);
        employee.setAvatar(avatarUrl);
        employeeService.updateEmployee(employee);

        Users currentUser = SessionHelper.getCurrentUser();
        Message message = new Message(MessageString.SUCCESS_CREATE_EMPLOYEE() + employee.getName(),"success",currentUser);
        messageService.createMessage(message);

        return  ResponseEntity.ok().body(new EmployeeResponse(employee));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
        Employee employeeObj = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(new EmployeeResponse(employeeObj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @ModelAttribute EmployeeRequest employeeRequest){
        Employee employeeObj = employeeService.getEmployeeById(id);

        employeeObj.setJoiningDay(CoverStringToTime.cover(employeeRequest.getJoiningDay()));
        employeeObj.setName(employeeRequest.getName());
        employeeObj.setTeam(employeeRequest.getTeam());

        employeeService.updateEmployee(employeeObj);

        Message message = new Message(MessageString.SUCCESS_UPDATE_EMPLOYEE() + employeeObj.getName(),"success",SessionHelper.getCurrentUser());
        messageService.createMessage(message);

        return ResponseEntity.ok(new EmployeeResponse(employeeObj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        Employee employeeObj = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(id);
        Message message = new Message(MessageString.SUCCESS_DELETE_EMPLOYEE() + employeeObj.getName(),"success",SessionHelper.getCurrentUser());
        messageService.createMessage(message);
        return ResponseEntity.ok(new EmployeeResponse(employeeObj));
    }
}
