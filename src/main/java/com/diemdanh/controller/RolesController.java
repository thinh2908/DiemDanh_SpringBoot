package com.diemdanh.controller;

import com.diemdanh.model.Roles;
import com.diemdanh.service.Impl.RolesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RolesServiceImpl rolesService;


    @GetMapping("getById/{id}")
    private Roles getRoleById(@PathVariable Long id){
        Roles role = rolesService.getRoleById(id);
        return role;
    };
}
