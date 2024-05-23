package com.diemdanh.config;

import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;
import com.diemdanh.repo.UsersRepository;
import com.diemdanh.service.Impl.EmployeeServiceImpl;
import com.diemdanh.service.Impl.RolesServiceImpl;
import com.diemdanh.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Component
public class DefaultUserConfig {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RolesServiceImpl rolesService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostConstruct
    public void createDefaultRole(){
//        Roles adminRole = new Roles("Administrator");
//        Roles managerRole = new Roles("Manager");
//        Roles userRole = new Roles("User");
//
//        rolesService.createRole(adminRole);
//        rolesService.createRole(managerRole);
//        rolesService.createRole(userRole);
//
//
//        Employee employee = new Employee();
//        employee.setName("Nguyen Van A");
//        employee.setTeam("Team A");
//        employee.setJoiningDay(CoverStringToTime.cover("2024-04-15T22:01:11.363Z"));
//        employee.setAvatar("http://localhost:1234/files/images/User1.jpg");
//        employee.setPosition("Backend Developer");
//        employeeService.createEmployee(employee);
//
//        Users defaultAdmin = new Users();
//        defaultAdmin.setUsername("admin");
//        defaultAdmin.setPassword("admin");
//        defaultAdmin.setEmployee(employee);
//
//        Roles defaultAdminRole = rolesService.getRoleById(Long.valueOf(1));
//        defaultAdmin.setRole(defaultAdminRole);
//        userService.createUser(defaultAdmin);
    }

    @PostConstruct
    public void createDefaultEmployee(){

    }

    @PostConstruct
    public void createDefaultAdminUser(){


    }



}
