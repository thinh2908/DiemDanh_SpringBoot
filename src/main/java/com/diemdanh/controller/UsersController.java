package com.diemdanh.controller;


import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.base.BaseFunction;
import com.diemdanh.factory.FilesStorageService;
import com.diemdanh.model.Book;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;
import com.diemdanh.request.ChangePasswordRequest;
import com.diemdanh.request.UserRequest;
import com.diemdanh.response.EmployeeResponse;
import com.diemdanh.response.UserResponse;
import com.diemdanh.service.Impl.EmployeeServiceImpl;
import com.diemdanh.service.Impl.RolesServiceImpl;
import com.diemdanh.service.Impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    FilesStorageService filesStorageService;

    @GetMapping("")
    public ResponseEntity<?> listAllUser(@RequestParam(required = false,value = "sort") String sort,
                                         @RequestParam(required = false,value = "range") String range,
                                         @RequestParam(required = false,value = "filter") String filter
    ) {
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
        Page<Users> pageUser;
        if (filter != "{}" && BaseFunction.stringToMap(filter).containsKey("username")) {
            pageUser = userService.findAllByUserName(BaseFunction.stringToMap(filter).get("username"), paging);
        }else
            pageUser = userService.findAll(paging);

        List<Users> list;
        list = pageUser.getContent();

        List<UserResponse> userResponses = list.stream()
                .map(UserResponse::new).collect(Collectors.toList());


        //Xu ly header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "users "+ rangeList.get(0) + "-" + rangeList.get(1) + "/" + userService.listUser().size());
        return  ResponseEntity.ok().headers(responseHeaders).body(userResponses);
    }
    @GetMapping("/getManyById")
    public ResponseEntity<?> listAllUserById(@RequestParam(required = false,value = "id") String ids) {
        List<Users> users = userService.ListUserById(BaseFunction.convertStringToListLong(ids));
        List<UserResponse> userResponseList = users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(userResponseList);
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
        updateUserObj.setPassword(updatedUserRequest.getPassword());
        Users userObj = userService.updateUser(updateUserObj);
        return ResponseEntity.ok(new UserResponse(userObj));
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteUser(@PathVariable Long id){
        Users userObj = userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Users user = SessionHelper.getCurrentUser();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.matches(changePasswordRequest.getCurrentPassword(),user.getPassword()));

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(),user.getPassword())){
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(changePasswordRequest.getNewPassword());
        user = userService.updateUser(user);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("/current-user")
    public ResponseEntity<?> currentUser() throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode json = mapper.readTree("{\"Greeting\": \"You are ADMIN\"}");
//        return ResponseEntity.ok(json);
        Users user = SessionHelper.getCurrentUser();
        return ResponseEntity.ok(new UserResponse(user));
    }


}
