package com.diemdanh.controller;


import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.model.Book;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;
import com.diemdanh.request.UserRequest;
import com.diemdanh.response.UserResponse;
import com.diemdanh.service.Impl.EmployeeServiceImpl;
import com.diemdanh.service.Impl.RolesServiceImpl;
import com.diemdanh.service.Impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserServiceImpl userService ;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private RolesServiceImpl rolesService;

    @GetMapping("")
    public ResponseEntity<?> listAllUser() {
        List<Users> users = userService.listUser();
        List<UserResponse> userResponseList = users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "users 0-1/"+users.size());

        return ResponseEntity.ok().headers(responseHeaders).body(userResponseList);
//        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getUserById(@PathVariable Long id){
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(new UserResponse(user));
    }


    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) throws JsonProcessingException {
        Employee employee = employeeService.getEmployeeById(userRequest.getEmployeeId());
        Users manager = (userRequest.getManagerId() != null) ? userService.getUserById(userRequest.getManagerId()) : null;
        Roles role = rolesService.getRoleById(userRequest.getRoleId());

        Long countEmployee = userService.getByEmployee(employee).stream().count();

        if (countEmployee == 1) return ResponseEntity.badRequest().build();
        Users userObj = userService.createUser(new Users(userRequest,employee,manager,role));
        return ResponseEntity.ok(new UserResponse(userObj));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest updatedUserRequest){

        Employee employee = employeeService.getEmployeeById(updatedUserRequest.getEmployeeId());
        Users manager = (updatedUserRequest.getManagerId() != null) ?
                        userService.getUserById(updatedUserRequest.getManagerId()) : null;
        Roles role = rolesService.getRoleById(updatedUserRequest.getRoleId());

        Users updateUserObj = userService.getUserById(id);
        updateUserObj.setUsername(updatedUserRequest.getUsername());
        updateUserObj.setEmployee(employee);
        updateUserObj.setManager(manager);
        updateUserObj.setRole(role);

        Users userObj = userService.updateUser(updateUserObj);
        return ResponseEntity.ok(new UserResponse(userObj));
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteUser(@PathVariable Long id){
        Users userObj = userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/current-user")
    public ResponseEntity<?> currentUser() throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode json = mapper.readTree("{\"Greeting\": \"You are ADMIN\"}");
//        return ResponseEntity.ok(json);

        return ResponseEntity.ok(SessionHelper.getCurrentUser());
    }

}
